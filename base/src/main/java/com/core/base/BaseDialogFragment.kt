package com.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

abstract class BaseDialogFragment<T : ViewDataBinding, V : BaseViewModel<*>> : DialogFragment() {
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

    private fun messageObserver() {
        mViewModel.message.observe(viewLifecycleOwner) {
            if (it is Int) {
                showMessage(it)
            } else {
                showMessage(it as String)
            }
        }
    }

    private fun isLoadingObserver() {
//        mViewModel.isLoading.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                showLoading()
//            } else {
//                hideLoading()
//            }
//        })
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
        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory(),
            )[mViewModel::class.java]
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
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
            baseActivity!!.showMessage(resId)
        }
    }

    fun showMessage(message: String) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(message)
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

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }
}
