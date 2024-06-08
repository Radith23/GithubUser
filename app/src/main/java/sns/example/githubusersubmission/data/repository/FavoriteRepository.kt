package sns.example.githubusersubmission.data.repository

import androidx.lifecycle.LiveData
import sns.example.githubusersubmission.data.local.entity.FavoriteUser
import sns.example.githubusersubmission.data.local.room.FavoriteDao
import sns.example.githubusersubmission.data.retrofit.ApiService
import sns.example.githubusersubmission.utils.AppExecutors

class FavoriteRepository(
    private val apiService: ApiService,
    private val userDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun getAllFavorite(): LiveData<List<FavoriteUser>> {
        return userDao.getAllFavorite()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return userDao.getFavoriteUserByUsername(username)
    }

    fun setFavoriteUser(user: FavoriteUser, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            user.isFavorite = favoriteState
            userDao.insert(user)
        }
    }

    fun deleteFavoriteUser(user: FavoriteUser, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            user.isFavorite = favoriteState
            userDao.delete(user)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}