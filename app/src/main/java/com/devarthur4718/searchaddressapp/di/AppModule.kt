package com.devarthur4718.searchaddressapp.di

import android.app.Application
import androidx.room.Room
import com.devarthur4718.searchaddressapp.BuildConfig
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.LocalAddressDatabase
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote.GitHubApi
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.repository.AddressRepositoryImpl
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.AddressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressRepository(
        api: GitHubApi,
        db: LocalAddressDatabase
    ): AddressRepository {
        return AddressRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideAddressDatabase(app: Application): LocalAddressDatabase {
        return Room.databaseBuilder(
            app,
            LocalAddressDatabase::class.java,
            "address_db"
        ).build()
    }
}
