package com.example.order.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.databinding.MainItemBinding
import java.util.*

class MainFragmentAdapter:RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var listItemData: List<ListItem> = listOf()
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setListItem(data: List<ListItem>) {
        listItemData = data
        notifyDataSetChanged()

    }

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listItemData[position])
    }

    override fun getItemCount() = listItemData.size
    inner class MainViewHolder(private val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(listItem: ListItem) {
            val textForItem:String =   listItem.documentFB

            binding.apply {

                mainItemRecyclerTextView.text = textForItem.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

                binding.mainItemRecyclerTextView.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(listItem)
                }
            }

        }


    }
}