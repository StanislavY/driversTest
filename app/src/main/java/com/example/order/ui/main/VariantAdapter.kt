package com.example.order.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.NotificationCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.order.R
import com.example.order.app.domain.model.ListItem
import com.example.order.core.GlobalConstAndVars
import com.example.order.databinding.QuestionsItemBinding
import kotlinx.android.synthetic.main.questions_item.view.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.*

class VariantAdapter: RecyclerView.Adapter<VariantAdapter.MainViewHolder>() {
    private var listItemData: List<ListItem> = listOf()
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null
    //выделить все цвета в ресурсы

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
        val binding = QuestionsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.bind(listItemData[position])
        if (GlobalConstAndVars.ANSWER_CLICKED.value=="") {
            holder.itemView.question_recycler_item.setBackgroundColor(Color.parseColor("#F3EFEF"))
        }

        if (listItemData[position].value == GlobalConstAndVars.ANSWER_CLICKED.value&&GlobalConstAndVars.ANSWER_CLICKED.value!="") {
            holder.itemView.question_recycler_item.setBackgroundColor(Color.parseColor("#4CAF50"))
          }

        if (listItemData[position].value == GlobalConstAndVars.CHOSEN_LIST_ITEM.value&&GlobalConstAndVars.ANSWER_CLICKED.value=="wrong") {


                holder.itemView.question_recycler_item.setBackgroundColor(Color.RED)
        }
        if (listItemData[position].value == GlobalConstAndVars.RIGHT_ANSWER.value&&GlobalConstAndVars.ANSWER_CLICKED.value=="wrong") {
            holder.itemView.question_recycler_item.setBackgroundColor(Color.parseColor("#4CAF50"))



        }


            }














    override fun getItemCount() = listItemData.size
    inner class MainViewHolder(private val binding: QuestionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(listItem: ListItem) {


            val textForItem:String =   listItem.value







            binding.apply {


                questionRecyclerItem.text = textForItem.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }


                binding.questionRecyclerItem.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(listItem)


                }
            }

        }


    }
    private val appCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, _ ->
            handleError()
        })

    private fun handleError() {}
}