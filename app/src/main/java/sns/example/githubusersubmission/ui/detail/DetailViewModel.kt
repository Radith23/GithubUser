package sns.example.githubusersubmission.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sns.example.githubusersubmission.data.response.DetailUsers
import sns.example.githubusersubmission.data.retrofit.ApiConfig

class DetailViewModel : ViewModel() {

    private val _user = MutableLiveData<DetailUsers>()
    val user: LiveData<DetailUsers> = _user

    private val _listUser = MutableLiveData<List<DetailUsers>>()
    val listUser: LiveData<List<DetailUsers>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "DetailViewModel"
    }

    fun getDetailUser(detail: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(detail)
        client.enqueue(object : Callback<DetailUsers> {
            override fun onResponse(call: Call<DetailUsers>, response: Response<DetailUsers>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}