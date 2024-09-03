package com.core.preference

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStores {
    suspend fun <T> getValue(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T>

    suspend fun <T> saveValue(
        key: Preferences.Key<T>,
        value: T,
    )

    suspend fun clearStore()
}
