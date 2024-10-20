package com.example.mydiaryapp.presentation.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentHomeBinding
import com.example.mydiaryapp.presentation.personal.PersonalActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val dashBoarViewModel by activityViewModels<DashBoardViewModel>()

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

        provideMenuOption()
        binding.cardviewDiary.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_Container_dashboard, DetailDiaryFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun provideMenuOption(){
        val toolbar = (activity as AppCompatActivity)
        toolbar.setSupportActionBar(binding.toolbar)
        toolbar.supportActionBar?.setDisplayShowTitleEnabled(false)
        //option menu
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.profile_menu ->{
                        startActivity(Intent(requireContext(), PersonalActivity::class.java))
                        true
                    }
                    else -> true
                }
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}