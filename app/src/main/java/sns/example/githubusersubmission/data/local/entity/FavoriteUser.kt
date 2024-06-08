package sns.example.githubusersubmission.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "userFavorite")
@Parcelize
data class FavoriteUser(
    @ColumnInfo (name = "username")
    @PrimaryKey
    var username: String = "",

    @ColumnInfo (name = "avatar")
    var avatarUrl: String? = null,

    @ColumnInfo (name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable
