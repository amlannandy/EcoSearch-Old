package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.*
import com.aknindustries.ecosearch.adaptors.MenuListAdaptor
import com.aknindustries.ecosearch.api.Auth
import com.aknindustries.ecosearch.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuItems = arrayOf("Profile", "Update Password", "Delete Account", "Log out")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.setSupportActionBarTitle(resources.getString(R.string.menu_title))
        setUpMenu()
    }

    private fun setUpMenu() {
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.menuRecyclerView.setHasFixedSize(true)
        val menuListAdaptor = MenuListAdaptor(requireContext(), menuItems, this)
        binding.menuRecyclerView.adapter = menuListAdaptor
    }

    fun menuItemOnClick(index: Int) {
        when (index) {
            0 -> goToProfile()
            1 -> goToUpdatePassword()
            2 -> goToDeleteAccount()
            3 -> logOut()
        }
    }

    private fun goToProfile() {
        val intent = Intent(activity, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun goToUpdatePassword() {
        val intent = Intent(activity, UpdatePasswordActivity::class.java)
        startActivity(intent)
    }

    private fun goToDeleteAccount() {
        val intent = Intent(activity, DeleteAccountActivity::class.java)
        startActivity(intent)
    }

    private fun logOut() {
        Auth(requireContext()).logOut(requireActivity())
        val intent = Intent(activity, SplashActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}