package com.example.mydiaryapp.presentation.dashboard

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.ActivityDashboardBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
     val binding by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null

        setupFragmentUI(HomeFragment())

        binding.fab.setOnClickListener {
            setupFragmentUI(AddFragment())
            binding.bottomNavigationView.selectedItemId = R.id.add_menu
        }
        binding.bottomNavigationView.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.home_menu -> {
                        setupFragmentUI(HomeFragment())
                        true
                    }
                    R.id.mood_menu -> {
                        setupFragmentUI(MoodFragment())
                        true
                    }
                    R.id.diary_menu -> {
                        setupFragmentUI(DiaryFragment())
                        true
                    }
                    R.id.setting_menu ->{
                        setupFragmentUI(SettingFragment())
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        })

    }

    private fun setupFragmentUI(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_Container_dashboard, fragment)
            .commit()
    }
}