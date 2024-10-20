package com.example.mydiaryapp.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentLoginBinding
import com.example.mydiaryapp.presentation.dashboard.DashboardActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels<AuthenticationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        buttonBackTop()
        buttonBackSetup()
        moveToRegisterSetup()

        binding.btnLogin.setOnClickListener {
            val username = binding.inputEditUsername.text.toString()
            val password = binding.inputEditPassword.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                val isValid = authenticationViewModel.loginAccount(username, password)
                if (isValid) {
                    startActivity(Intent(requireActivity(), DashboardActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireActivity(), "Data salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //setup toolbar
    private fun toolbarSetup() {
        val toolbarActivity = (activity as AppCompatActivity)
        toolbarActivity.setSupportActionBar(binding.toolbar)
        toolbarActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarActivity.supportActionBar?.setHomeButtonEnabled(true)
        toolbarActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //Button back toolbar
    private fun buttonBackTop() {
        val menu: MenuHost = requireActivity()
        menu.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        requireActivity().finish()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    //Button back
    private fun buttonBackSetup() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    //Button move to register
    private fun moveToRegisterSetup() {
        binding.labelAlternativeAuthentication.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_authentication, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}