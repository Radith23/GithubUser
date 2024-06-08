package sns.example.githubusersubmission.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import sns.example.githubusersubmission.adapter.FavoriteAdapter
import sns.example.githubusersubmission.data.response.ItemsItem
import sns.example.githubusersubmission.databinding.ActivityFavoriteBinding
import sns.example.githubusersubmission.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        binding.rvFavorite.adapter = adapter

        setRecyclerView()

        val factory = ViewModelFactory.getInstance(this)
        favoriteViewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        favoriteViewModel.getAllFavorite().observe(this) { user ->
            val items = arrayListOf<ItemsItem>()
            user.map {
                val item = ItemsItem(
                    login = it.username,
                    avatarUrl = it.avatarUrl ?: "",
                    gistsUrl = "",
                    reposUrl = "",
                    followingUrl = "",
                    starredUrl = "",
                    followersUrl = "",
                    type = "",
                    url = "",
                    subscriptionsUrl = "",
                    score = 0,
                    receivedEventsUrl = "",
                    eventsUrl = "",
                    htmlUrl = "",
                    siteAdmin = false,
                    id = 0,
                    gravatarId = "",
                    nodeId = "",
                    organizationsUrl = ""
                )
                items.add(item)
            }
            adapter.submitList(items)
        }
    }

    private fun setRecyclerView() {
        val layoutmanager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutmanager
        binding.rvFavorite.setHasFixedSize(true)
    }
}