package com.example.mydiaryapp.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentOnBoardingFourBinding
import com.example.mydiaryapp.databinding.FragmentOnBoardingOneBinding
import com.example.mydiaryapp.databinding.FragmentOnBoardingThreeBinding

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
            //TODO save state login = true
            //TODO move to main activity
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}