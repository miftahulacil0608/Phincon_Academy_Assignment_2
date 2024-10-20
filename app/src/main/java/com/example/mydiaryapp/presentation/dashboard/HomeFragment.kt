package com.example.mydiaryapp.presentation.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentHomeBinding
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.presentation.Helper.toFormatDatesUI
import com.example.mydiaryapp.presentation.dashboard.DetailDiaryFragment.Companion.KEY_DIARY_ID
import com.example.mydiaryapp.presentation.personal.PersonalActivity

@SuppressLint("NewApi")
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dashBoardViewModel: DashBoardViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerUser()
        observerDiary()
        provideViewAll()

    }

    private fun observerUser(){
        dashBoardViewModel.getUser().observe(viewLifecycleOwner){
            binding.tvSayHelloUser.text = resources.getString(R.string.label_say_hello, it.username)
        }
    }

    private fun provideViewAll(){
        binding.tvViewAll.setOnClickListener {
            (activity as DashboardActivity).binding.bottomNavigationView.selectedItemId = R.id.diary_menu
        }
    }

    private fun observerDiary(){
        dashBoardViewModel.getDiaryRecentlyAdded().observe(viewLifecycleOwner){
            if (it!=null){
                showDiary(it)
            }else{
                displayEmptyState()
            }
        }
    }

    private fun showDiary(diary:Diary){
        binding.tvDate.text = diary.diaryDate.toFormatDatesUI()
        binding.titleItemDiary.text = diary.diaryHeadline
        binding.descriptionItemDiary.text = diary.diaryMessage
        binding.cardviewDiary.setOnClickListener {
            openDetailFragment(diary)
        }
    }

    private fun displayEmptyState(){
        binding.tvDate.text = ""
        binding.titleItemDiary.text = "Not Found Diary"
        binding.descriptionItemDiary.text = ""
        binding.cardviewDiary.setOnClickListener(null)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openDetailFragment(diary: Diary){
        val bundle = Bundle().apply {
            putParcelable(KEY_DIARY_ID, diary)
            putString("SOURCE_FRAGMENT", "home")
        }
        val detailFragment = DetailDiaryFragment().apply {
            arguments = bundle
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_Container_dashboard, detailFragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}