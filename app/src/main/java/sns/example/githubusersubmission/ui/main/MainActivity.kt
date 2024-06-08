package sns.example.githubusersubmission.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import sns.example.githubusersubmission.R
import sns.example.githubusersubmission.adapter.UsersAdapter
import sns.example.githubusersubmission.data.response.ItemsItem
import sns.example.githubusersubmission.databinding.ActivityMainBinding
import sns.example.githubusersubmission.ui.favorite.FavoriteActivity
import sns.example.githubusersubmission.ui.theme.ThemePreferences
import sns.example.githubusersubmission.ui.theme.ThemeViewModel
import sns.example.githubusersubmission.ui.theme.dataStore
import sns.example.githubusersubmission.utils.ThemeModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private var searchUsers: String? = null
    private var defaultUsers = "Radith"
    private var switchTheme: Boolean = false

    companion object {
        private const val TAG = "searchUsers"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyTheme()

        val switchTheme = findViewById<Switch>(R.id.switch_theme)

        val pref = ThemePreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        mainViewModel.listUser.observe(this) { users ->
            if (users.isNullOrEmpty()) {
                Toast.makeText(this, R.string.user_unknown, Toast.LENGTH_SHORT).show()
            } else {
                setUsersData(users)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        searchUsers = savedInstanceState?.getString("searchUsers")
        if (!searchUsers.isNullOrEmpty()) {
            mainViewModel.findUsers(searchUsers!!)
            binding.searchBar.setText(searchUsers)
        } else {
            mainViewModel.findUsers(defaultUsers)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchUsers = searchView.text.toString()
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.findUsers(searchUsers!!)
                    false
                }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favPage -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun applyTheme() {
        val pref = ThemePreferences.getInstance(this.application.dataStore)
        val settingViewModel = ViewModelProvider(this, ThemeModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TAG, searchUsers)
    }

    private fun setUsersData(users: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}