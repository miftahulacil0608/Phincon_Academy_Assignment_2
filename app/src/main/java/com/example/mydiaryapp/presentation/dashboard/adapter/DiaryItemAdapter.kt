package com.example.mydiaryapp.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiaryapp.databinding.ItemDiaryLayoutBinding
import com.example.mydiaryapp.domain.model.Diary
import com.example.mydiaryapp.presentation.Helper.toFormatDatesUI

class DiaryItemAdapter(private var listItem:List<Diary> = emptyList(), private val listenerItem:ItemClickListener):RecyclerView.Adapter<DiaryItemAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemDiaryLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Diary){
            binding.tvDate.text = item.diaryDate.toFormatDatesUI()
            binding.titleItemDiary.text = item.diaryHeadline
            binding.descriptionItemDiary.text = item.diaryMessage

            binding.root.setOnClickListener{
                listenerItem.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDiaryLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun initNewValue(newItemList:List<Diary>){
        listItem = newItemList
        notifyDataSetChanged()
    }
}