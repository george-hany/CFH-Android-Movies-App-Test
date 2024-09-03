package com.app.app.app

import androidx.multidex.MultiDexApplication
import com.app.app.BuildConfig
import com.core.preference.sharedPref.SharedPref
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : MultiDexApplication() {
    companion object {
        lateinit var pref: SharedPref
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        pref = SharedPref(this, BuildConfig.APPLICATION_ID)
        AndroidThreeTen.init(this)
    }
}
