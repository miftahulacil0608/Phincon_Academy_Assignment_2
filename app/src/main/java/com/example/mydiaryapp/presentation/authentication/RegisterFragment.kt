package com.example.mydiaryapp.presentation.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    private val database: DiaryDatabase by lazy {
        DiaryDatabase.getInstance(requireActivity().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarActivity = (activity as AppCompatActivity)
        toolbarActivity.setSupportActionBar(binding.toolbar)
        toolbarActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarActivity.supportActionBar?.setHomeButtonEnabled(true)
        toolbarActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.labelAlternativeAuthentication.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_authentication, LoginFragment())
                .addToBackStack(null)
                .commit()
        }


        binding.btnRegister.setOnClickListener {
            val username = binding.inputEditUsername.text.toString()
            val email = binding.inputEditEmail.text.toString()
            val password = binding.inputEditPassword.text.toString()
            /*lifecycleScope.launch {
                database.userDao().addUser(UserEntity(id = null, username, email, password))
            }*/

            lifecycleScope.launch {
                val isValid = authenticationViewModel.registerNewAccount(User(username=username, email=email, password = password))
                if (isValid){
                    startActivity(Intent(requireActivity(), DashboardActivity::class.java))
                    requireActivity().finish()
                }else{
                    Toast.makeText(requireActivity(), "data dobel", Toast.LENGTH_SHORT).show()
                }
            }

            //clear menggunakan callback aja gak masalah
        }

    }

    //TODO dispatcher bisa dirubah ko nanti
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                true
            }
            else -> {
                false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}