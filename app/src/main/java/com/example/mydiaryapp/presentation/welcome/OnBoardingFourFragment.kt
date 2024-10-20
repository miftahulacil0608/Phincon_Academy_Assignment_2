package com.example.mydiaryapp.presentation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.mydiaryapp.R
import com.example.mydiaryapp.presentation.authentication.AuthenticationActivity
import com.example.mydiaryapp.presentation.authentication.RegisterFragment
import com.example.mydiaryapp.databinding.FragmentOnBoardingFourBinding

class OnBoardingFourFragment : Fragment() {
    private var _binding: FragmentOnBoardingFourBinding? = null
    private val binding: FragmentOnBoardingFourBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingFourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextOnBoardingFour.setOnClickListener {
            startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}