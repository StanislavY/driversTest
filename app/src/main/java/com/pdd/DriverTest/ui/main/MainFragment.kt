package com.pdd.DriverTest.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdd.DriverTest.R
import com.pdd.DriverTest.app.domain.model.ListItem
import com.pdd.DriverTest.core.GlobalConstAndVars
import com.pdd.DriverTest.databinding.MainFragmentBinding
import com.pdd.DriverTest.viewModel.MainViewModel
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
                    setDefaults()
                    binding.image.setImageResource(showImage(listItem.documentFB))
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_VARIANT_FIELD,listItem.documentFB)
                    questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_QUESTION_FIELD,listItem.documentFB)
                    binding.questionsTextTextView.text = GlobalConstAndVars.QUESTION_TEXT
                    binding.theme.text="Билет 1, вопрос ${listItem.documentFB}"//заглушка - узнать у заказчика релизовывать ли что-то большее
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.IMAGE_URL_NAME,listItem.documentFB)

                }

            }
        })
        questionsAdapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {
                  appCoroutineScope.launch {
                    setDefaults()
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_ANSWER_FIELD,listItem.documentFB)
                    if ( viewModel.isAnswerRight(GlobalConstAndVars.ANSWER_NUMBER,listItem.field)) {
                        GlobalConstAndVars.ANSWER_CLICKED = listItem
                        GlobalConstAndVars.CHOSEN_LIST_ITEM=listItem
                        GlobalConstAndVars.RIGHT_ANSWER=listItem
                        questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)

                    }
                    else {
                        GlobalConstAndVars.ANSWER_CLICKED.value="wrong"//заглушка
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
            binding.theme.text="Билет 1, вопрос ${GlobalConstAndVars.START_TICKET}"//заглушка - узнать у заказчика релизовывать ли что-то большее
           }

    }

    private fun setDefaults() {
        GlobalConstAndVars.ANSWER_CLICKED = ListItem("", "", "", "", "", "")
        GlobalConstAndVars.RIGHT_ANSWER = ListItem("", "", "", "", "", "")
        GlobalConstAndVars.CHOSEN_LIST_ITEM = ListItem("", "", "", "", "", "")
    }




    private fun showImage(questionNumber:String): Int {
        //узнать у заказчика хочет ли он делать автоматическую подгрузку картинок из фаербейса, или ему хватит в раках mvp такой реализации

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
        if (questionNumber == "9") {
            return R.drawable.pdd_1_9
        } else {


            return R.drawable.pdd_0

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




