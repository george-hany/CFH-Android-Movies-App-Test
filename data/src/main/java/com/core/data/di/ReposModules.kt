package com.core.data.di

import com.core.data.network.ApiFactory
import com.core.data.repos.AuthRepo
import com.core.data.repos.HomeRepo
import com.core.data.repos.LoginRepo
import com.core.data.repos.MainRepo
import com.core.network.MockedNetwork
import com.core.network.NetworkFactory
import com.core.network.NetworkFactoryInterface
import com.core.preference.DataStores
import com.core.preference.securedSharedPreferences.SecuredDataStore
import com.core.utils.FileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ReposModules {
    @Provides
    @ViewModelScoped
    fun provideMainRepo(
        apiFactory: ApiFactory,
        dataStore: SecuredDataStore,
        mockedNetwork: MockedNetwork,
    ): MainRepo {
        return MainRepo(
            apiFactory,
            dataStore,
            mockedNetwork,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAuthRepo(
        apiFactory: ApiFactory,
        dataStore: DataStores,
        network: NetworkFactoryInterface,
    ): AuthRepo {
        return AuthRepo(
            apiFactory,
            dataStore,
            network,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSplashRepo(
        apiFactory: ApiFactory,
        dataStore: SecuredDataStore,
        networkFactory: NetworkFactory,
        fileManager: FileManager,
    ): HomeRepo {
        return HomeRepo(apiFactory, dataStore, networkFactory, fileManager)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginRepo(
        apiFactory: ApiFactory,
        dataStore: SecuredDataStore,
        networkFactory: NetworkFactory,
        fileManager: FileManager,
    ): LoginRepo {
        return LoginRepo(apiFactory, dataStore, networkFactory, fileManager)
    }
}
