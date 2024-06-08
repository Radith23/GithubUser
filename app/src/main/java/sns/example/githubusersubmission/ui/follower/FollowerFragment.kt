package sns.example.githubusersubmission.ui.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import sns.example.githubusersubmission.adapter.UsersAdapter
import sns.example.githubusersubmission.data.response.ItemsItem
import sns.example.githubusersubmission.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private var position = 1
    private var username: String = ""
    private val followerViewModel by viewModels<FollowerViewModel>()

    companion object {
        const val ARG_POSITION = "0"
        const val ARG_USERNAME = "radit"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowerBinding.bind(view)

        followerViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollower.layoutManager = layoutManager

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        if (position == 1) {
            followerViewModel.listUsers.observe(viewLifecycleOwner) {
                setUserData(it)
            }
            followerViewModel.loadFollower(username)
        } else {
            followerViewModel.listUsers.observe(viewLifecycleOwner) {
                setUserData(it)
            }
            followerViewModel.loadFollowing(username)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setUserData(userList: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(userList)
        binding.rvFollower.adapter = adapter
    }
}