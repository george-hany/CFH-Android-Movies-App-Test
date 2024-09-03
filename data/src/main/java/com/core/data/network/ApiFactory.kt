package com.core.data.network

import com.core.data.BuildConfig
import com.core.data.network.interfaces.AccountApis
import com.core.network.RetrofitClient
import com.core.preference.sharedPref.SharedPref
import javax.inject.Inject

class ApiFactory
    @Inject
    constructor(private var pref: SharedPref) {
        fun getAccountApis(): AccountApis {
            return RetrofitClient(pref)
                .retrofit(BuildConfig.BASE_URL)
                .create(AccountApis::class.java)
        }
    }
