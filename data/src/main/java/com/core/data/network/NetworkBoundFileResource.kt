package com.core.data.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.core.data.BuildConfig
import com.core.data.MainExceptions
import com.core.data.R
import com.core.data.network.model.ResponseState
import com.core.data.strategy.DataStrategy
import com.core.network.NetworkFactory
import com.core.network.NetworkFactoryInterface
import com.core.network.model.Result
import com.core.utils.CommonUtils.getMessage
import com.core.utils.FileManager
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class NetworkBoundFileResource<ResultType : Any>
@MainThread constructor(
    var networkFactory: NetworkFactoryInterface,
    strategy: DataStrategy.Strategies = DataStrategy.Strategies.DEFAULT_STRATEGY,
    var fileName: String = "",
    var fileManager: FileManager?,
) {
    private val result = MediatorLiveData<ResultType?>()

    private val resultState = MutableStateFlow<ResponseState<ResultType?>>(ResponseState.Loading)

    init {
        setLoading()
        if (networkFactory.isNetworkConnected()) {
            fetchFromNetwork(strategy)
        } else {
            if (strategy == DataStrategy.Strategies.NETWORK_ONLY) {
                setErrorResult(-1, R.string.noInternetConnection)
                onFetchFailed(
                    MainExceptions(
                        MainExceptions.ExceptionsTypes.NO_INTERNET_CONNECTIVITY.string,
                    ),
                )
            } else if (strategy == DataStrategy.Strategies.DEFAULT_STRATEGY) {
                loadDataFromDB()
            }
        }
    }

    private fun setLoading() {
        resultState.value = ResponseState.Loading
    }

    private fun setSuccessResult(res: ResultType?) {
        resultState.value = ResponseState.Success(res)
    }

    private fun setErrorResult(
        code: Int?,
        message: Any?,
    ) {
        resultState.value = ResponseState.Error(code, message)
    }

    @MainThread
    private fun setValue(newValue: ResultType?) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchFromNetwork(dataStrategy: DataStrategy.Strategies) {
        GlobalScope.launch(Dispatchers.Main) {
            val apiResponse = createCall()
            if (networkFactory is NetworkFactory) {
                try {
                    val response =
                        networkFactory.makeRequest(call = apiResponse, errorMessage = "sadsa")
                    when (response) {
                        is Result.Success -> {
                            if (response.data.code() == 200 || response.data.code() == 201 || response.data.code() == 204) {
                                if (dataStrategy == DataStrategy.Strategies.NETWORK_ONLY) {
                                    loadDataFromNetwork(response)
                                } else if (dataStrategy == DataStrategy.Strategies.DEFAULT_STRATEGY) {
                                    saveCallResultInDB(processResponse(response).body())
                                    loadDataFromDB()
                                }
                            } else {
                                setErrorResult(
                                    response.data.code(),
                                    getMessage(
                                        response.data.errorBody()?.toString(),
                                    ),
                                )
                                setEmptyObject()
                            }
                        }

                        is Result.Error -> {
                            if (BuildConfig.DEBUG) {
                                setErrorResult(
                                    response.data.code(),
                                    getMessage(
                                        response.data.errorBody()?.toString(),
                                    ),
                                )
                            } else {
                                setErrorResult(-1, R.string.noInternetConnection)
                            }
                            setEmptyObject()
                        }

                        else -> {
                        }
                    }
                } catch (e: Exception) {
                    onFetchFailed(MainExceptions(e.message))
                    if (BuildConfig.DEBUG) {
                        setErrorResult(e.hashCode(), e.message)
                    } else {
                        setErrorResult(-1, R.string.noInternetConnection)
                    }
                    setEmptyObject()
                } finally {
//                    setErrorResult(-2)
                }
            } else {
                val dataWrapper = dataWrapper(convert(networkFactory.getStringJson()))
                result.addSource(dataWrapper) { newData ->
                    result.removeSource(dataWrapper)
                    setValue(newData)
                }
                setSuccessResult(dataWrapper.value)
            }
        }
    }

    private fun setEmptyObject() {
        val mutableLiveData = MutableLiveData<ResultType?>()
        mutableLiveData.value = null
        result.addSource(
            mutableLiveData,
        ) { newData ->
            setValue(newData)
        }
    }

    private fun loadDataFromNetwork(response: Result.Success<ResultType>) {
        val dataSource = dataWrapper(processResponse(response).body())
        result.addSource(dataSource) { newData ->
            result.removeSource(dataSource)
            setValue(newData)
        }
        setSuccessResult(processResponse(response).body())
    }

    private fun loadDataFromDB() {
        val loadFromDb = loadFromDb()
        result.addSource(loadFromDb) { newData ->
            result.removeSource(loadFromDb)
            setValue(newData)
        }
        if (loadFromDb.value != null)
            setSuccessResult(loadFromDb.value)
    }

    protected fun onFetchFailed(exception: MainExceptions) {}

    fun asLiveData() = result as LiveData<ResultType>

    @WorkerThread
    protected open fun processResponse(response: Result.Success<ResultType>) = response.data

    @WorkerThread
    private fun saveCallResultInDB(data: ResultType?) {
        val toJson = Gson().toJson(data)
        fileManager?.writeFile(toJson, fileName)
    }

    @MainThread
    protected fun shouldFetch(): Boolean {
        return false
    }

    @MainThread
    protected abstract fun convert(json: String): ResultType?

    @MainThread
    private fun loadFromDb(): LiveData<ResultType?> {
        val mutableLiveData = MutableLiveData<ResultType?>()
        val readFile = fileManager?.readFile(fileName)
        if (readFile != null) {
            mutableLiveData.value = convert(readFile)
        } else {
            if (networkFactory.isNetworkConnected().not())
                setErrorResult(
                    -1,
                    R.string.noInternetConnection
                )
            onFetchFailed(MainExceptions("File Not Found"))
        }
        return mutableLiveData
    }

    @MainThread
    private fun dataWrapper(data: ResultType?): LiveData<ResultType?> {
        val mutableLiveData = MutableLiveData<ResultType?>()
        mutableLiveData.value = data
        return mutableLiveData
    }

    @MainThread
    protected abstract suspend fun createCall(): suspend () -> Response<ResultType>

    fun asFlow() = resultState
}
