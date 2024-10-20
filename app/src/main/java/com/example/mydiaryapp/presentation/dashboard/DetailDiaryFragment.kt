package com.example.mydiaryapp.presentation.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FragmentDetailDiaryBinding
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.presentation.Helper.toFormatDatesUI
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

    private val sourceFragment by lazy {
        arguments?.getInt("SOURCE_FRAGMENT")
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

        provideToolbar()

        userData?.let {
            dashBoardViewModel.getItemDiary(it.diaryId)
        }

        setupItemDiary()

    }

    private fun provideToolbar(){
        val toolbar = (activity as AppCompatActivity)
        toolbar.setSupportActionBar(binding.toolbar)
        toolbar.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.supportActionBar?.setHomeButtonEnabled(true)

        requireActivity().addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_item_detail_diary, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    android.R.id.home ->{
                        if (sourceFragment!=null){
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_Container_dashboard, HomeFragment())
                                .commit()
                        }else{
                            parentFragmentManager.popBackStack()
                        }
                        true
                    }
                    R.id.item_share_menu -> {
                        true
                        //implicit intent
                    }
                    R.id.item_edit_menu -> {
                        editData()
                        true
                    }
                    R.id.item_delete_menu->{
                        deleteItemDiary()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



    private fun setupItemDiary() {
        dashBoardViewModel.itemDiary.observe(viewLifecycleOwner) {
            binding.tvTitleDate.text = it?.diaryDate?.toFormatDatesUI()
            binding.titleItemDiary.text = it?.diaryHeadline
            binding.tvDate.text = it?.diaryDate?.toFormatDatesUI()
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