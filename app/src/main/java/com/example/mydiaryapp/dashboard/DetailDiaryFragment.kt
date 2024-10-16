package com.example.mydiaryapp.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentDetailDiaryBinding

class DetailDiaryFragment : Fragment() {

    private var _binding: FragmentDetailDiaryBinding? = null
    private val binding: FragmentDetailDiaryBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menu.setOnClickListener {
            menu()
        }
    }

    private fun menu() {
        //TODO icon show
        val popupMenu = PopupMenu(requireContext(), binding.menu)
        popupMenu.inflate(R.menu.menu_item_detail_diary)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {

                return when (item.itemId) {
                    R.id.item_share_menu -> {
                        //itent implicit ke wa atau apapun yg terima text
                        Toast.makeText(requireContext(), "share", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.item_edit_menu -> {
                        //ke halaman create tapi sambil membawa data
                        Toast.makeText(requireContext(), "edit", Toast.LENGTH_SHORT).show()

                        true
                    }

                    R.id.item_delete_menu -> {
                        //delete filenya lalu pindah ke halaman diary
                        Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()

                        true
                    }

                    else -> false
                }
            }
        })
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}