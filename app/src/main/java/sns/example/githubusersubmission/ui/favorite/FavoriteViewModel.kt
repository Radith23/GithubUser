package sns.example.githubusersubmission.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sns.example.githubusersubmission.data.repository.FavoriteRepository
import sns.example.githubusersubmission.data.local.entity.FavoriteUser

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getAllFavorite(): LiveData<List<FavoriteUser>> {
        return favoriteRepository.getAllFavorite()
    }

    fun getFavUser(username: String): LiveData<FavoriteUser> =
        favoriteRepository.getFavoriteUserByUsername(username)

    fun saveFavorite(favoriteUser: FavoriteUser) {
        favoriteRepository.setFavoriteUser(favoriteUser, true)
    }

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        favoriteRepository.deleteFavoriteUser(favoriteUser, false)
    }
}