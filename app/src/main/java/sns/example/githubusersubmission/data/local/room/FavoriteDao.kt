package sns.example.githubusersubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sns.example.githubusersubmission.data.local.entity.FavoriteUser

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userFavorite: FavoriteUser)

    @Query("SELECT * FROM userFavorite WHERE isFavorite = 1")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM userFavorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Delete
    fun delete(favoriteUser: FavoriteUser)
}