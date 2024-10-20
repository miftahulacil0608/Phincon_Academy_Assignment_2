package com.example.mydiaryapp.presentation.dashboard.modificationDiary

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.ActivityAddAndUpdateDiaryBinding
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.presentation.Helper.toFormatDatesUI
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddAndUpdateDiaryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddAndUpdateDiaryBinding.inflate(layoutInflater)
    }
    private val addUpdateActivityViewModel: AddUpdateActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        binding.tvDate.text = dates().toFormatDatesUI()
        val getIntentValue = getIntentValue()

        getIntentValue?.let {
            binding.tvDate.text = it.diaryDate.toFormatDatesUI()
            binding.textInputEditHeadline.setText(it.diaryHeadline)
            binding.textInputEditBody.setText(it.diaryMessage)
            binding.btnSubmit.text = resources.getString(R.string.label_edit)
        }

        binding.btnSubmit.setOnClickListener {
            val isValid = provideValidation()
            if (isValid){
                val headline = binding.textInputEditHeadline.text.toString()
                val message = binding.textInputEditBody.text.toString()
                val diaryDate = dates()
                if (getIntentValue != null) {
                    editDiary(
                        userOwnerId = getIntentValue.userOwnerId,
                        diaryId = getIntentValue.diaryId,
                        newHeadline = headline,
                        newMessage = message,
                        diaryDate = diaryDate
                    )
                } else {
                    addDiary(headline = headline, message = message, diaryDate = diaryDate)
                }
            }

        }
        //TODO checking input cek kalo kosong gabisa di submit lah ya

        backPressed()
    }

    private fun provideValidation(): Boolean {
        if (binding.textInputEditHeadline.text?.isEmpty() == true) {
            binding.textInputEditHeadline.error = "Not Null"
            return false
        } else {
            binding.textInputEditHeadline.error = null
            return true
        }
    }

    private fun getIntentValue(): Diary? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("KEY_TO_UPDATE", Diary::class.java)
        } else {
            intent.getParcelableExtra("KEY_TO_UPDATE")
        }
    }

    private fun dates(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm:ss")
        val current = currentDateTime.format(formatter)

        return current
    }

    private fun addDiary(headline: String, message: String, diaryDate: String) {
        addUpdateActivityViewModel.addDiary(headline, message, diaryDate)
        Toast.makeText(
            this@AddAndUpdateDiaryActivity,
            "Data Berhasil ditambahkan",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun editDiary(
        userOwnerId: Int,
        diaryId: Int,
        newHeadline: String,
        newMessage: String,
        diaryDate: String
    ) {
        addUpdateActivityViewModel.updateDiary(
            Diary(
                userOwnerId,
                diaryId,
                newHeadline,
                newMessage,
                diaryDate
            )
        )
        Toast.makeText(this@AddAndUpdateDiaryActivity, "Data Berhasil dirubah", Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    private fun backPressed() {
        onBackPressedDispatcher.addCallback(
            this@AddAndUpdateDiaryActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
    }
}