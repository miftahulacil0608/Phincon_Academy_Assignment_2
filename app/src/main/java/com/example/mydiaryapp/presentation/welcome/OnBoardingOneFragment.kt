package com.example.mydiaryapp.presentation.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentOnBoardingOneBinding

class OnBoardingOneFragment : Fragment() {

    private var _binding: FragmentOnBoardingOneBinding? = null
    private val binding: FragmentOnBoardingOneBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_down_enter, R.anim.slide_down_exit)
                .replace(R.id.fragment_container, OnBoardingTwoFragment())
                .addToBackStack(null)
                .commit()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}