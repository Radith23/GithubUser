package sns.example.githubusersubmission.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sns.example.githubusersubmission.data.response.ItemsItem
import sns.example.githubusersubmission.data.response.Users
import sns.example.githubusersubmission.data.retrofit.ApiConfig

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<Users>()
    val users: LiveData<Users> = _users

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "MainViewModel"
    }

    fun findUsers(search: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(search)
        client.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}