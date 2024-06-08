package sns.example.githubusersubmission.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import sns.example.githubusersubmission.R
import sns.example.githubusersubmission.adapter.DetailPageAdapter
import sns.example.githubusersubmission.data.local.entity.FavoriteUser
import sns.example.githubusersubmission.data.response.DetailUsers
import sns.example.githubusersubmission.databinding.ActivityDetailBinding
import sns.example.githubusersubmission.ui.favorite.FavoriteViewModel
import sns.example.githubusersubmission.utils.ViewModelFactory
import sns.example.githubusersubmission.utils.setImageUrl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorited: Boolean = false
    private lateinit var avatarUrl: String

    companion object {
        var EXTRA_LOGIN = "extra_login"
        var EXTRA_AVATAR = "extra_avatar"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        val login = intent.getStringExtra(EXTRA_LOGIN).toString()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val detailPageAdapter = DetailPageAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = detailPageAdapter
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailViewModel.getDetailUser(login)
        detailPageAdapter.username = login

        detailViewModel.user.observe(this) { user ->
            showUserData(user)
        }

        viewModel.getFavUser(login).observe(this) {
            isFavorited = it != null
            updateFavIcon()
        }

        binding.addFavorite.setOnClickListener {
            if (!isFavorited) {
                viewModel.saveFavorite(FavoriteUser(login, avatarUrl))
                Toast.makeText(
                    this@DetailActivity,
                    "User has been added to Favorite",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.deleteFavorite(FavoriteUser(login, avatarUrl))
                Toast.makeText(
                    this@DetailActivity,
                    "User has been removed from Favorite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateFavIcon() {
        val favIcon = if (isFavorited) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.addFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.addFavorite.context,
                favIcon
            )
        )
    }

    private fun showUserData(user: DetailUsers) {
        binding.detailPhoto.setImageUrl(user.avatarUrl)
        binding.username.text = user.login
        binding.name.text = user.name
        "${user.followers} followers".also { binding.followers.text = it }
        "${user.following} following".also { binding.following.text = it }
        avatarUrl = user.avatarUrl.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }
}