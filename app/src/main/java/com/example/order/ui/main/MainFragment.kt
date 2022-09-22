package com.example.order.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.order.R
import com.example.order.app.domain.model.ListItem
import com.example.order.core.GlobalConstAndVars
import com.example.order.databinding.MainFragmentBinding
import com.example.order.viewModel.MainViewModel
import kotlinx.coroutines.*


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val ticketsAdapter = MainFragmentAdapter()
    private val questionsAdapter = VariantAdapter()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ticketsAdapter.removeOnItemViewClickListener()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         ticketsAdapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {
                appCoroutineScope.launch {
                    setDefaultVals()
                    binding.image.setImageResource(showImage(listItem.documentFB))
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_VARIANT_FIELD,listItem.documentFB)
                    questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_QUESTION_FIELD,listItem.documentFB)
                    binding.questionsTextTextView.text = GlobalConstAndVars.QUESTION_TEXT
                    binding.theme.text="Билет 1, вопрос ${listItem.documentFB}"
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.IMAGE_URL_NAME,listItem.documentFB)

                }

            }
        })
        questionsAdapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {
                appCoroutineScope.launch {
                    setDefaultVals()
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_ANSWER_FIELD,listItem.documentFB)
                    if ( viewModel.isAnswerRight(GlobalConstAndVars.ANSWER_NUMBER,listItem.field)) {
                        GlobalConstAndVars.ANSWER_CLICKED = listItem
                        GlobalConstAndVars.CHOSEN_LIST_ITEM=listItem
                        GlobalConstAndVars.RIGHT_ANSWER=listItem
                        questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
                    }
                    else {
                        GlobalConstAndVars.ANSWER_CLICKED.value="wrong"
                        GlobalConstAndVars.CHOSEN_LIST_ITEM=listItem
                        viewModel.detectRightAnswerFromList(GlobalConstAndVars.QUESTIONS_LIST,GlobalConstAndVars.ANSWER_NUMBER)
                        questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
                    }

                    }
            }
        })
        binding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.mainFragmentRecyclerView.adapter = ticketsAdapter
        binding.questionsRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.questionsRecycler.adapter = questionsAdapter
        appCoroutineScope.launch {
            binding.image.setImageResource(showImage(GlobalConstAndVars.START_TICKET))
            viewModel.processTheSelectedItemTicket()
            ticketsAdapter.setListItem(GlobalConstAndVars.TICKETS_LIST)
            viewModel.processTheSelectedItemQuestion(GlobalConstAndVars.NAME_VARIANT_FIELD,GlobalConstAndVars.START_TICKET)
            questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
            viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_QUESTION_FIELD,GlobalConstAndVars.START_TICKET)
            binding.questionsTextTextView.text = GlobalConstAndVars.QUESTION_TEXT
            binding.theme.text="Билет 1, вопрос ${GlobalConstAndVars.START_TICKET}"
          }
    }

    private fun setDefaultVals() {
        GlobalConstAndVars.ANSWER_CLICKED = GlobalConstAndVars.DEFAULT_LIST_ITEM
        GlobalConstAndVars.RIGHT_ANSWER = GlobalConstAndVars.DEFAULT_LIST_ITEM
        GlobalConstAndVars.CHOSEN_LIST_ITEM = GlobalConstAndVars.DEFAULT_LIST_ITEM
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main_botom_bar, menu)
    }
    private fun showImage(questionNumber:String): Int {
        //это заглушка, т к реализацию обмена с фаербейсом отрезал

        if (questionNumber == "2") {
            return R.drawable.pdd_1_2
        }
        if (questionNumber == "3") {
            return R.drawable.pdd_1_3
        }
        if (questionNumber == "4") {
            return R.drawable.pdd_1_4
        }
        if (questionNumber == "5") {
            return R.drawable.pdd_1_5
        }
        if (questionNumber == "8") {
            return R.drawable.pdd_1_8
        }
        return if (questionNumber == "9") {
            R.drawable.pdd_1_9
        } else {
            R.drawable.pdd_0

        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(listItem: ListItem)
    }

    private val appCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, _ ->
            handleError()
        })

    private fun handleError() {}

    companion object {
        fun newInstance()= MainFragment()
        }
 }




