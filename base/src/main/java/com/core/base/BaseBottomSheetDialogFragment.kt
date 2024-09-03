package com.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.transition.TransitionInflater
import com.core.utils.CommonUtils.getWindowHeight
import com.core.utils.hideSoftKeyboard
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding, V : BaseViewModel<*>> :
    BottomSheetDialogFragment() {
    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?,
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)

    var behavior: BottomSheetBehavior<View>? = null

    override fun onStart() {
        super.onStart()
        setupDialogBehavior()
    }

    private fun setupDialogBehavior() {
        behavior = BottomSheetBehavior.from(requireView().parent as View)
        val layoutParams: ViewGroup.LayoutParams = requireView().layoutParams
        val windowHeight = requireActivity().getWindowHeight()
        layoutParams.height = windowHeight
        requireView().layoutParams = layoutParams
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        behavior?.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(
                    bottomSheet: View,
                    newState: Int,
                ) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        dialog?.dismiss()
                    }
                }

                override fun onSlide(
                    bottomSheet: View,
                    slideOffset: Float,
                ) {
                }
            },
        )
    }

    override fun onPause() {
        super.onPause()
        behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().hideSoftKeyboard()
    }

    var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    lateinit var viewDataBinding: T
    lateinit var mViewModel: V

    lateinit var navigation: NavController

    var isSubFragment = true

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
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

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
}
