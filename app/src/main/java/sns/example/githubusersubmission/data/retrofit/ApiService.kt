package sns.example.githubusersubmission.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sns.example.githubusersubmission.data.response.DetailUsers
import sns.example.githubusersubmission.data.response.ItemsItem
import sns.example.githubusersubmission.data.response.Users

interface ApiService {

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<Users>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUsers>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}