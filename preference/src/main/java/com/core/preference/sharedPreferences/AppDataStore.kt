package com.core.preference.sharedPreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.core.preference.DataStores
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore
    @Inject
    constructor(val context: Context, prefName: String) :
    DataStores {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = prefName)

        override suspend fun <T> getValue(
            key: Preferences.Key<T>,
            defaultValue: T,
        ): Flow<T> {
            return context.dataStore.data.map { pref ->
                pref[key] ?: defaultValue
            }
        }

        override suspend fun <T> saveValue(
            key: Preferences.Key<T>,
            value: T,
        ) {
            context.dataStore.edit { pref ->
                pref[key] = value
            }
        }

        override suspend fun clearStore() {
            context.dataStore.edit { pref ->
                pref.clear()
            }
        }
    }
