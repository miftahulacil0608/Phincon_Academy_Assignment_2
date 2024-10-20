package com.example.mydiaryapp.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.data.source.local.room.DiaryDatabase
import com.example.mydiaryapp.data.model.UserEntity
import com.example.mydiaryapp.databinding.FragmentRegisterBinding
import com.example.mydiaryapp.domain.model.User
import com.example.mydiaryapp.presentation.dashboard.DashboardActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels<AuthenticationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        buttonBackBottom()
        buttonBackTop()
        moveToLogin()

        binding.btnRegister.setOnClickListener {
            val isValidInput = validationInput()
            if (isValidInput){
                val username = binding.inputEditUsername.text.toString()
                val email = binding.inputEditEmail.text.toString()
                val password = binding.inputEditPassword.text.toString()

                viewLifecycleOwner.lifecycleScope.launch {
                    val isValidRegister = authenticationViewModel.registerNewAccount(
                        User(
                            username = username,
                            email = email,
                            password = password
                        )
                    )
                    if (isValidRegister) {
                        startActivity(Intent(requireActivity(), DashboardActivity::class.java))
                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireActivity(), "data dobel", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun validationInput(): Boolean {
        if (binding.inputEditUsername.text.toString().length < 4) {
            binding.inputEditUsername.error = "Minimum 4 length"
        }

        if (binding.inputEditUsername.text.toString().contains(" ")) {
            binding.inputEditUsername.error = "No space"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEditEmail.text.toString()).matches()) {
            binding.inputEditEmail.error = "Wrong format"
            return false
        }
        if (binding.inputEditPassword.text?.isEmpty() == true) {
            binding.inputEditUsername.error = "No empty string"
        }

        if (binding.inputEditPassword.text.toString().length < 8) {
            binding.inputEditPassword.error = "Minimum 8 length"
            return false
        }

        else {
            binding.inputLayoutUsername.error = null
            binding.inputLayoutEmail.error = null
            binding.inputLayoutPassword.error = null
            return true
        }
    }

    //Toolbar setup
    private fun toolbarSetup() {
        val toolbarActivity = (activity as AppCompatActivity)
        toolbarActivity.setSupportActionBar(binding.toolbar)
        toolbarActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarActivity.supportActionBar?.setHomeButtonEnabled(true)
        toolbarActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun buttonBackTop() {
        val menu: MenuHost = requireActivity()
        menu.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        parentFragmentManager.popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun moveToLogin(){
        binding.labelAlternativeAuthentication.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    //Button back setup
    private fun buttonBackBottom() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}