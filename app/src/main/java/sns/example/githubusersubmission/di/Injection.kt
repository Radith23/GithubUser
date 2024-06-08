package sns.example.githubusersubmission.di

import android.content.Context
import sns.example.githubusersubmission.data.local.room.FavoriteDatabase
import sns.example.githubusersubmission.data.repository.FavoriteRepository
import sns.example.githubusersubmission.data.retrofit.ApiConfig
import sns.example.githubusersubmission.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.FavoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}