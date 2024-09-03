package com.core.data.di

import android.content.Context
import com.core.data.network.ApiFactory
import com.core.network.MockedNetwork
import com.core.network.NetworkFactory
import com.core.network.NetworkFactoryInterface
import com.core.preference.DataStores
import com.core.preference.securedSharedPreferences.SecuredDataStore
import com.core.preference.sharedPref.SharedPref
import com.core.preference.sharedPreferences.AppDataStore
import com.core.utils.FileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext appContext: Context,
    ) = appContext

    @Provides
    @Singleton
    fun provideNetworkFactory(
        @ApplicationContext context: Context,
    ): NetworkFactory = NetworkFactory(context)

    @Provides
    @Singleton
    fun provideMockedNetwork(
        @ApplicationContext context: Context,
    ): MockedNetwork = MockedNetwork(context)

    @Provides
    @Singleton
    fun provideNetworkFactoryInterface(networkFactory: NetworkFactory): NetworkFactoryInterface = networkFactory

    @Provides
    @Singleton
    fun provideFileManager(
        @ApplicationContext context: Context,
    ): FileManager = FileManager(context)

    @Provides
    @Singleton
    fun provideAppDataStore(
        @ApplicationContext context: Context,
        name: String,
    ): AppDataStore = AppDataStore(context, name)

    @Provides
    @Singleton
    fun provideSecuredDataStore(
        @ApplicationContext context: Context,
        name: String,
    ): SecuredDataStore = SecuredDataStore(context, name)

    @Provides
    @Singleton
    fun provideDataStores(securedDataStore: SecuredDataStore): DataStores = securedDataStore

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext context: Context,
        name: String,
    ): SharedPref = SharedPref(context, name)

    @Provides
    @Singleton
    fun provideApiFactory(sharedPref: SharedPref): ApiFactory = ApiFactory(sharedPref)
}
