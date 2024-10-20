package com.example.mydiaryapp.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.FilterLayoutBinding
import com.example.mydiaryapp.databinding.FragmentDiaryBinding
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.presentation.dashboard.adapter.DiaryItemAdapter
import com.example.mydiaryapp.presentation.dashboard.adapter.ItemClickListener

class DiaryFragment : Fragment(), ItemClickListener, SearchView.OnQueryTextListener {
    private var _binding: FragmentDiaryBinding? = null
    private val binding: FragmentDiaryBinding get() = _binding!!
    private val dashBoardViewModel by activityViewModels<DashBoardViewModel>()

    private lateinit var adapter: DiaryItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataListDiary()


        dashBoardViewModel.getResultSortingListDiary().observe(viewLifecycleOwner){
            adapter.initNewValue(it)
        }
        setupRecyclerView()
        binding.sorting.setOnClickListener {
            sortingDialog()
        }

        //searchview
        val searchView = binding.searchview
        searchView.setOnQueryTextListener(this)

    }

    private fun sortingDialog() {
        val filterBinding = FilterLayoutBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setCancelable(false)
        dialog.setView(filterBinding.root)

        dashBoardViewModel.getSettingDiary().observe(viewLifecycleOwner){
            Log.d("setting value", "sortingDialog: ${it.sortBy} ${it.orderBy}")
            when(it.sortBy){
                "headline"->filterBinding.byHeadline.isChecked = true
                "date" -> filterBinding.byDate.isChecked = true
            }
            when(it.orderBy){
                "ASC" -> filterBinding.byAscending.isChecked = true
                "DESC" -> filterBinding.byDescending.isChecked = true
            }
        }

        filterBinding.btnApply.setOnClickListener {
            val sortByRadio = filterBinding.radioGroupSortBy.checkedRadioButtonId
            val orderByRadio = filterBinding.radioGroupRangeBy.checkedRadioButtonId

            val sortBy = when(sortByRadio){
                filterBinding.byHeadline.id -> {"headline"}
                filterBinding.byDate.id -> {"date"}
                else -> {"headline"}
            }
            val orderBy = when(orderByRadio){
                filterBinding.byAscending.id -> {"ASC"}
                filterBinding.byDescending.id -> {"DESC"}
                else->{"ASC"}
            }

            dashBoardViewModel.saveSettingDiary(sortBy, orderBy)

            dialog.dismiss()
        }

        filterBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupDataListDiary() {
        dashBoardViewModel.getListDiary()
    }

    private fun setupRecyclerView() {
        adapter = DiaryItemAdapter(listenerItem = this@DiaryFragment)
        binding.rvDiary.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvDiary.layoutManager = linearLayoutManager
    }

    private fun searchDatabase(query: String) {
        val querySearch = "%$query%"
        dashBoardViewModel.search(querySearch)
            .observe(viewLifecycleOwner) { resultListDiary ->
                adapter.initNewValue(resultListDiary)
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    @SuppressLint("NewApi")
    override fun onClick(diary: Diary) {
        val bundle = Bundle()
        bundle.putParcelable(DetailDiaryFragment.KEY_DIARY_ID, diary)
        val fragment = DetailDiaryFragment()
        fragment.arguments = bundle

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_Container_dashboard, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}