package com.example.cashmanager.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cashmanager.R
import com.example.cashmanager.ui.home.HomeViewModel

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        val viewModel: HomeViewModel by activityViewModels()
        homeViewModel = viewModel

        val userEmail: EditText = root.findViewById(R.id.userEmail)
        val userUsername: EditText = root.findViewById(R.id.userUsername)
        val updateAccount: Button = root.findViewById(R.id.updateAccount)

        userEmail.addTextChangedListener {
            updateAccount.isEnabled = (userEmail.text.toString() != homeViewModel.currentUser.value?.email && userEmail.text.isNotEmpty())
        }

        userUsername.addTextChangedListener {
            updateAccount.isEnabled = (userUsername.text.toString() != homeViewModel.currentUser.value?.username && userUsername.text.isNotEmpty())
        }

        updateAccount.setOnClickListener{
            val email : String = userEmail.text.toString()
            val username : String = userUsername.text.toString()
            val validToast = Toast.makeText(activity?.applicationContext, "Account successfully updated !", Toast.LENGTH_LONG)
            val invalidToast = Toast.makeText(activity?.applicationContext, "Error during update of your account", Toast.LENGTH_LONG)
            homeViewModel.updateUser(email, username, validToast, invalidToast)
        }

        homeViewModel.currentUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                userEmail.setText(it.email)
                userUsername.setText(it.username)
                updateAccount.isEnabled = false
            }
        })
        return root
    }
}