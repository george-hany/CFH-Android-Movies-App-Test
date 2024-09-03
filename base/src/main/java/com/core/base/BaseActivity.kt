package com.core.base

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.core.utils.dialog.LoaderDialog.showLoaderDialog
import com.core.utils.dialog.NoteDialog.showMessageDialog
import timber.log.Timber

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {
    private var mProgressDialog: Dialog? = null
    lateinit var viewDataBinding: T
    private lateinit var mViewModel: V

    private lateinit var navigation: NavController

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

    @IdRes
    abstract fun controllerId(): Int

    abstract fun getViewModel(): V

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setupLanguage()
        super.onCreate(savedInstanceState)
        initViewModel()
        performDataBinding()
        initNavigationController()
        isLoadingObserver()
        messageObserver()
        exceptionMessageObserver()
    }

    abstract fun setupLanguage()

    private fun exceptionMessageObserver() {
        mViewModel.dataResources.exceptionMessage.observe(this) {
            if (it is Int) {
                showNoteDialog(it)
            } else {
                showNoteDialog(it as String)
            }
        }
    }

    private fun messageObserver() {
        mViewModel.message.observe(this) {
            it?.let {
                if (it is Int) {
                    showNoteDialog(it)
                } else {
                    showNoteDialog(it as String)
                }
            }
        }
    }

    private fun isLoadingObserver() {
        mViewModel.isLoading.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog?.dismiss()
        }
    }

    fun showNoteDialog(message: String) {
        showMessageDialog(message) {}
    }

    fun showNoteDialog(message: Int) {
        showMessageDialog(message) {}
    }

    fun showMessage(message: String) {
        toast(message)
    }

    fun showMessage(
        @StringRes resId: Int,
    ) {
        showMessage(getString(resId))
    }

    fun showLog(message: String) {
        Timber.e(message)
    }

    fun showLog(
        @StringRes resId: Int,
    ) {
        showLog(getString(resId))
    }

    fun showLoading() {
        if (mProgressDialog == null || mProgressDialog?.isShowing == false) {
            mProgressDialog =
                showLoaderDialog().apply { show() }
        }
    }

    private fun Context.toast(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
    ) = Toast.makeText(this, message, duration).show()

    private fun initViewModel() {
        mViewModel = getViewModel()
        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory(),
            )[mViewModel::class.java]
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewDataBinding.setVariable(bindingVariable(), mViewModel)
        viewDataBinding.lifecycleOwner = this
    }

    private fun initNavigationController() {
        if (controllerId() != 0) {
            navigation = Navigation.findNavController(this, controllerId())
        }
    }
}
