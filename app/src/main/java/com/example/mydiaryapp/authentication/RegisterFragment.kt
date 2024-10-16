package com.example.mydiaryapp.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
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

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {

                true
            }
            else -> {false}
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}