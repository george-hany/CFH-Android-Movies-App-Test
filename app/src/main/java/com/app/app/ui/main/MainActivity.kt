package com.app.app.ui.main

import androidx.activity.viewModels
import com.app.app.BR
import com.app.app.R
import com.app.app.app.MyApp.Companion.pref
import com.app.app.databinding.ActivityMainBinding
import com.core.base.BaseActivity
import com.core.utils.LangUtil.setLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    val mainViewModel: MainViewModel by viewModels()

    override fun bindingVariable(): Int {
        return BR.viewModel
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun controllerId(): Int {
        return R.id.mainController
    }

    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }

    override fun setupLanguage() {
        val lang = pref.getLang()
        setLanguage(lang)
    }
}
