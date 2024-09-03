package com.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.core.utils.hideSoftKeyboard

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment() {
    var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    lateinit var viewDataBinding: T
    lateinit var mViewModel: V

    lateinit var navigation: NavController

    var isSubFragment = false

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun bindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun getViewModel(): V

    /**
     * Override for set view model
     *
     * @return view model instance
     */

    fun Context.toast(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
    ) = Toast.makeText(this, message, duration).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun messageObserver() {
        mViewModel.message.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                if (it is Int) {
                    showMessage(it)
                } else {
                    showMessage(it as String)
                }
                mViewModel.message.value = null
            }
        }
    }

    private fun isLoadingObserver() {
        mViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mRootView = viewDataBinding.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onAttach(context: Context) {
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
        }
        super.onAttach(context)
    }

    private fun initViewModel() {
        mViewModel = getViewModel()
//        mViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        ).get(mViewModel::class.java)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        isLoadingObserver()
        messageObserver()
        performDataBinding()
        initNavigationController(view)
        exceptionMessageObserver()
    }

    private fun exceptionMessageObserver() {
        mViewModel.dataResources.exceptionMessage.observe(viewLifecycleOwner) {
            if (it is Int) {
                showMessage(it)
            } else {
                showMessage(it as String)
            }
        }
    }

    private fun initNavigationController(view: View) {
        if (!isSubFragment) {
            navigation = Navigation.findNavController(view)
        }
    }

    private fun performDataBinding() {
        viewDataBinding.setVariable(bindingVariable(), mViewModel)
        viewDataBinding.lifecycleOwner = this
    }

    fun showMessage(resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.showNoteDialog(resId)
        }
    }

    fun showMessage(message: String) {
        if (baseActivity != null) {
            baseActivity!!.showNoteDialog(message)
        }
    }

    fun showLoading() {
        if (baseActivity != null) {
            baseActivity!!.showLoading()
        }
    }

    fun hideLoading() {
        if (baseActivity != null) {
            baseActivity!!.hideLoading()
        }
    }

    fun showLog(message: String) {
        if (baseActivity != null) {
            baseActivity!!.showLog(message)
        }
    }

    fun showLog(resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.showLog(resId)
        }
    }

    override fun onPause() {
        super.onPause()
        requireContext().hideSoftKeyboard()
    }
}
