package com.core.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.core.data.base.BaseRepo

open class BaseViewModel<D : BaseRepo>(
    val dataResources: D,
) : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var message = MutableLiveData<Any?>()

    fun setIsLoading(boolean: Boolean) {
        isLoading.postValue(boolean)
    }

    fun onBack(v: View) {
        v.findNavController().popBackStack()
    }

    fun getLang() = dataResources.getLangFromSharedPref()
}
