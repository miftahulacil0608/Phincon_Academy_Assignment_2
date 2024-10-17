package com.example.mydiaryapp.presentation.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mydiaryapp.databinding.ActivitySplashScreenBinding
import com.example.mydiaryapp.presentation.authentication.AuthenticationViewModel
import com.example.mydiaryapp.presentation.dashboard.DashboardActivity
import com.example.mydiaryapp.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch{
            delay(3000L)
            splashScreenViewModel.session.observe(this@SplashScreenActivity){
                if (it?.id==0){
                    Log.d("gagal masul Id ", "Id =  ${it?.id}")
                    startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
                    finish()
                }else{
                    Log.d("berhasil masuk Id ", "Id= ${it?.id}")
                    startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
                    finish()
                }
            }
        }
    }
}