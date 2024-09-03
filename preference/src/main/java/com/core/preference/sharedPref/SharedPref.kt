package com.core.preference.sharedPref

import android.content.Context
import android.content.SharedPreferences
import com.core.preference.PreferencesKeys.Companion.lang
import com.core.preference.PreferencesKeys.Companion.token
import com.core.preference.PreferencesKeys.Companion.userId
import javax.inject.Inject

class SharedPref
    @Inject
    constructor(
        context: Context,
        prefName: String,
    ) {
        private val mPrefs: SharedPreferences =
            context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

        fun getLang(): String {
            return mPrefs.getString(lang.name, "en")!!
        }

        fun saveLang(value: String) {
            mPrefs.edit().putString(lang.name, value).apply()
        }

        fun getUserId(): String {
            return mPrefs.getString(userId.name, "")!!
        }

        fun saveUserId(value: String) {
            mPrefs.edit().putString(userId.name, value).apply()
        }

        fun getToken(): String {
            return mPrefs.getString(token.name, "")!!
        }

        fun saveToken(value: String) {
            mPrefs.edit().putString(token.name, value).apply()
        }
    }
