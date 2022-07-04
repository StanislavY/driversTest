package com.example.order.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.databinding.MainItemBinding

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

            val textForItem:String = if (GlobalConstAndVars.LIST_KEY == "0"&&GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST==0) {


                    listItem.field + ": " + listItem.value


            }


            else {
                if (listItem.collection == "ВР"&&listItem.value!="") {
                    listItem.value + ": " + listItem.field
                } else {
                   listItem.field
                }
            }
            if (GlobalConstAndVars.LIST_KEY == "0"&&GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST==1) {
                listItem.documentFB

            }


            binding.apply {

                mainItemRecyclerTextView.text = textForItem

                binding.mainItemRecyclerTextView.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(listItem)
                }
            }

        }


    }
}