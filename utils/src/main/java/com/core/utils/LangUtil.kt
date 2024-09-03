@file:Suppress("DEPRECATION")

package com.core.utils
import android.app.Activity
import android.content.res.Configuration
import java.util.Locale

object LangUtil {
    fun Activity.setLanguage(lang: String) {
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val res = resources
        val dm = res.displayMetrics
        val conf = Configuration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        onConfigurationChanged(conf)
    }
}
