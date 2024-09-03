package com.core.preference

import androidx.datastore.preferences.core.stringPreferencesKey

class PreferencesKeys {
    companion object {
        val lang = stringPreferencesKey(name = "lang")
        val data = stringPreferencesKey(name = "data")
        val userData = stringPreferencesKey(name = "userData")
        val homeData = stringPreferencesKey(name = "homeData")
        val info = stringPreferencesKey(name = "info")
        val userId = stringPreferencesKey(name = "userId")
        val token = stringPreferencesKey(name = "token")
    }
}
