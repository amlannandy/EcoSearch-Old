package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.SplashActivity
import com.aknindustries.ecosearch.api.Auth

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        textView.text = "Logout"
        textView.setOnClickListener {
            Auth(requireContext()).logOut(requireActivity(), this)
        }
        return root
    }

    fun logOut() {
        val intent = Intent(activity, SplashActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}