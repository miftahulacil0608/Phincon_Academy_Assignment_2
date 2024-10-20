package com.example.mydiaryapp.presentation.personal

import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.ActivityPersonalBinding
import com.example.mydiaryapp.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonalActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPersonalBinding.inflate(layoutInflater)
    }
    private val personalActivityViewModel:PersonalActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        provideToolbar()
        provideData()
        editData()

    }

    private fun provideToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun provideData(){
        val getIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_TO_EDIT_DATA,User::class.java)
        } else {
            intent.getParcelableExtra(KEY_TO_EDIT_DATA)
        }

        getIntent?.let {
            binding.inputEditUsername.setText(it.username)
            binding.inputEditEmail.setText(it.email)
            binding.inputEditPassword.setText(it.password)
        }
    }
    private fun editData(){
        binding.btnRegister.setOnClickListener {
            val isValid = validationInput()
            if (isValid){
                val newUsername = binding.inputEditUsername.text.toString()
                val newEmail = binding.inputEditEmail.text.toString()
                val newPassword = binding.inputEditPassword.text.toString()

                lifecycleScope.launch {
                    val updateIsValid = personalActivityViewModel.updateData(User(username = newUsername, email = newEmail, password = newPassword))
                    if (updateIsValid){
                        Toast.makeText(this@PersonalActivity, "Data berhasil dirubah", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@PersonalActivity, "Data gagal dirubah", Toast.LENGTH_SHORT).show()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    companion object{
        const val KEY_TO_EDIT_DATA = "KEY TO EDIT DATA"
    }
}