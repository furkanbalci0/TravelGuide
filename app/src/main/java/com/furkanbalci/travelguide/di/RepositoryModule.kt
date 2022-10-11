package com.furkanbalci.travelguide.di


import com.furkanbalci.travelguide.data.datasource.db.locale.DbLocalDataSource
import com.furkanbalci.travelguide.data.datasource.mock.remote.MockRetrofitDataSource
import com.furkanbalci.travelguide.data.datasource.triposo.remote.TriposoRetrofitDataSource
import com.furkanbalci.travelguide.data.repositories.*
import com.furkanbalci.travelguide.data.repositories.impl.DbRepositoryImpl
import com.furkanbalci.travelguide.data.repositories.impl.MockApiRepositoryImpl
import com.furkanbalci.travelguide.data.repositories.impl.TriposoApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTriposoRepository(
        dataSource: TriposoRetrofitDataSource
    ): TriposoApiRepository {
        return TriposoApiRepositoryImpl(dataSource)
    }
    
    @Provides
    @Singleton
    fun provideDbRepository(
        dataSource: DbLocalDataSource
    ): DbRepository {
        return DbRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideMockRepository(
        dataSource: MockRetrofitDataSource
    ): MockApiRepository {
        return MockApiRepositoryImpl(dataSource)
    }

}