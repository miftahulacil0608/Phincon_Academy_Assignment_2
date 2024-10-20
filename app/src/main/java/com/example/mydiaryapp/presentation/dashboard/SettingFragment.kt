package com.example.mydiaryapp.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentHomeBinding
import com.example.mydiaryapp.databinding.FragmentSettingBinding
import com.example.mydiaryapp.presentation.authentication.AuthenticationActivity
import com.example.mydiaryapp.presentation.personal.PersonalActivity

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val dashBoardViewModel by activityViewModels<DashBoardViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashBoardViewModel.getUser().observe(viewLifecycleOwner){userData->
            binding.btnEditAccount.setOnClickListener {
                val intent = Intent(requireContext(), PersonalActivity::class.java).apply {
                    putExtra(PersonalActivity.KEY_TO_EDIT_DATA, userData)
                }
                startActivity(intent)
            }
        }

        binding.btnEditLogout.setOnClickListener {
            dashBoardViewModel.logout()
            startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}