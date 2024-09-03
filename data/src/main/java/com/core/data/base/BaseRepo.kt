package com.core.data.base

import androidx.lifecycle.MutableLiveData
import com.core.network.NetworkFactoryInterface
import com.core.preference.DataStores
import com.core.preference.sharedPref.SharedPref

open class BaseRepo(
    val dataStore: DataStores,
    val networkFactory: NetworkFactoryInterface,
    val pref: SharedPref? = null,
) {
    var exceptionMessage = MutableLiveData<Any>()

    fun getLangFromSharedPref() = pref?.getLang()

    suspend fun clearUserData() {
        dataStore.clearStore()
        pref?.saveUserId("")
    }
}
