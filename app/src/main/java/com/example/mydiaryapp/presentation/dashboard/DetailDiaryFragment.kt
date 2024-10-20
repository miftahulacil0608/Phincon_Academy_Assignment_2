package com.example.mydiaryapp.presentation.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentDetailDiaryBinding
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.presentation.dashboard.modificationDiary.AddAndUpdateDiaryActivity

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class DetailDiaryFragment : Fragment() {

    private var _binding: FragmentDetailDiaryBinding? = null
    private val binding: FragmentDetailDiaryBinding get() = _binding!!
    private val dashBoardViewModel: DashBoardViewModel by activityViewModels()
    private val userData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_DIARY_ID, Diary::class.java)
        } else {
            arguments?.getParcelable<Diary>(KEY_DIARY_ID)
        }
    }

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

        userData?.let {
            dashBoardViewModel.getItemDiary(it.diaryId)
        }

        setupItemDiary()

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
                        editData()
                        true
                    }

                    R.id.item_delete_menu -> {
                        //delete filenya lalu pindah ke halaman diary
                        deleteItemDiary()
                        true
                    }

                    else -> false
                }
            }
        })
        popupMenu.show()
    }


    private fun setupItemDiary() {
        dashBoardViewModel.itemDiary.observe(viewLifecycleOwner) {
            binding.titleItemDiary.text = it?.diaryHeadline
            binding.descriptionItemDiary.text = it?.diaryMessage
        }
    }

    private fun editData(){
        val intent = Intent(requireContext(), AddAndUpdateDiaryActivity::class.java)
        intent.putExtra("KEY_TO_UPDATE", userData)
        startActivity(intent)
    }

    private fun deleteItemDiary() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Yakin pengen dihapus?")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Iyaa", object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                userData?.let {
                    dashBoardViewModel.deleteItemDiary(it)
                }
                dialog?.dismiss()
                parentFragmentManager.popBackStack()
            }
        })

        alertDialog.setNegativeButton("Tidak", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        })
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_DIARY_ID = "KEY_DIARY_ID"
    }

}