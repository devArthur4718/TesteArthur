package com.devarthur4718.searchaddressapp.di

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote.GitHubApi
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.GithubRepository
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.GithubRepositoryImpl
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

//    @Provides
//    @Singleton
//    fun provideAddressDatabase(app: Application): LocalAddressDatabase {
//        return Room.databaseBuilder(
//            app,
//            LocalAddressDatabase::class.java,
//            LocalAddressDatabase.DATABASE_NAME
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAddressRepository(db: LocalAddressDatabase): LocalAddressRepository {
//        return LocalAddressRepositoryImpl(db.localAddressDao)
//    }

    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GitHubApi.BASE_URL)
            .build()
            .create(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubRepository(api: GitHubApi): GithubRepository {
        return GithubRepositoryImpl(api)
    }
}
