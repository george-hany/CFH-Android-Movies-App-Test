package com.core.data.repos

import com.core.data.base.BaseRepo
import com.core.data.network.ApiFactory
import com.core.network.NetworkFactoryInterface
import com.core.preference.DataStores
import com.core.preference.PreferencesKeys
import javax.inject.Inject

class AuthRepo
    @Inject
    constructor(
        var apiFactory: ApiFactory,
        dataStore: DataStores,
        networkFactory: NetworkFactoryInterface,
    ) : BaseRepo(dataStore, networkFactory) {
        suspend fun saveToken(token: String) = dataStore.saveValue(PreferencesKeys.token, token)
    }
