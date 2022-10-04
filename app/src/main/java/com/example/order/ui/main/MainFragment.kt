package com.example.order.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.order.R
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.usecase.*
import com.example.order.core.GlobalConstAndVars
import com.example.order.databinding.MainFragmentBinding
import com.example.order.viewModel.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
    private val fireBase:FireBaseCase=FirebaseCaseImpl()
    private val load:LoadDataFrom1CCase=LoadDataFrom1CCaseImpl()
    private val list:CreateListOfAllItemsFrom1CDBCase=CreateListOfAllItemsFrom1CDBCaseImpl()
    private val storage = Firebase.storage
    private val storageRef = storage.reference




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

        /*  binding.buttonNextQuestion.setOnClickListener{
              *//*questionsAdapter.onItemVi*//*


        }*/









        ticketsAdapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {

                appCoroutineScope.launch {
                    GlobalConstAndVars.ANSWER_CLICKED=ListItem("","","","","","")
                    GlobalConstAndVars.RIGHT_ANSWER=ListItem("","","","","","")
                    GlobalConstAndVars.CHOSEN_LIST_ITEM=ListItem("","","","","","")
                    /*GlobalConstAndVars.CURRENT_QUESTION=listItem.documentFB*/
                    binding.image.setImageResource(showImage(listItem.documentFB))
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_VARIANT_FIELD,listItem.documentFB)
                    questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_QUESTION_FIELD,listItem.documentFB)
                    binding.questionsTextTextView.text = GlobalConstAndVars.QUESTION_TEXT
                    binding.theme.text="Билет 1, вопрос ${listItem.documentFB}"
                    viewModel.getQuestionsAndAnswers(GlobalConstAndVars.IMAGE_URL_NAME,listItem.documentFB)
                    /*storage.getReferenceFromUrl(GlobalConstAndVars.PICTURES_URL).downloadUrl.addOnSuccessListener {
                        Glide
                            .with(this@MainFragment)
                            .load(it)
                            .into(binding.image)
                    }.addOnFailureListener {

                    }*/

                    /*  @BindingAdapter("app:imageUri")
                      fun loadImageWithUri(imageView: ImageView, imageUri: String){
                          Glide.with(imageView.context).load(Uri.parse(imageUri)).into(imageView)
                      }*/
                    /*   val islandRef = storageRef.child("ТестыПДД/1-10.jpg")

                       val localFile = File.createTempFile("1-12", ".jpg")

                       islandRef.getFile(localFile).addOnSuccessListener {
                           // Local temp file has been created
                       }.addOnFailureListener {
                           // Handle any errors
                       }*/
                }

                /* chooseScreenToShow(listItem)*/
            }
        })
        questionsAdapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {

                /*GlobalConstAndVars.CLICKED_LIST_ITEM=LIST*/



                /* viewModel.rememberListOfChosenItemsVM(listItem)*/
                appCoroutineScope.launch {
                    GlobalConstAndVars.ANSWER_CLICKED=ListItem("","","","","","")
                    GlobalConstAndVars.RIGHT_ANSWER=ListItem("","","","","","")
                    GlobalConstAndVars.CHOSEN_LIST_ITEM=ListItem("","","","","","")

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
        /* val layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,true)
         recyclerview.layoutManager = layoutManager*/

        binding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.mainFragmentRecyclerView.adapter = ticketsAdapter
        binding.questionsRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.questionsRecycler.adapter = questionsAdapter
        /* viewModel.processTheSelectedItemTicket()*/
        appCoroutineScope.launch {
            binding.image.setImageResource(showImage(GlobalConstAndVars.START_TICKET))
            /*  GlobalConstAndVars.CURRENT_QUESTION=GlobalConstAndVars.START_TICKET*/
            viewModel.processTheSelectedItemTicket()
            ticketsAdapter.setListItem(GlobalConstAndVars.TICKETS_LIST)
            viewModel.processTheSelectedItemQuestion(GlobalConstAndVars.NAME_VARIANT_FIELD,GlobalConstAndVars.START_TICKET)
            questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)
            viewModel.getQuestionsAndAnswers(GlobalConstAndVars.NAME_QUESTION_FIELD,GlobalConstAndVars.START_TICKET)
            binding.questionsTextTextView.text = GlobalConstAndVars.QUESTION_TEXT
            binding.theme.text="Билет 1, вопрос ${GlobalConstAndVars.START_TICKET}"
            /* viewModel.getQuestionsAndAnswers(GlobalConstAndVars.IMAGE_URL_NAME,GlobalConstAndVars.START_TICKET)
             storage.getReferenceFromUrl(GlobalConstAndVars.PICTURES_URL).downloadUrl.addOnSuccessListener {
                Glide
                    .with(this@MainFragment)
                    .load(it)
                    .into(binding.image)
                 }.addOnFailureListener {

             }*/








        }



/*
        ticketsAdapter.setListItem(GlobalConstAndVars.TICKETS_LIST)
        questionsAdapter.setListItem(GlobalConstAndVars.QUESTIONS_LIST)*/
        /* viewModel.processAppState().observe(viewLifecycleOwner, { renderList(it) })




             viewModel.processTheSelectedItemQuestion("variant","1")
         viewModel.processAppState().observe(viewLifecycleOwner, { renderList(it) })
         viewModel.processTheSelectedItemTicket()*/


        /* appCoroutineScope.launch {
             viewModel.processAppState().observe(viewLifecycleOwner, { renderQuestionsList(it) })
             viewModel.processTheSelectedItemQuestion("variant","1")
             }*/

        /*   appCoroutineScope.launch{
     viewModel.processAppState().observe(viewLifecycleOwner, { renderQuestionsList(it) })
     viewModel.processTheSelectedItemQuestion("variant","1")}  */


        /*launchSearchBarListener()
        isEditingWorkedOutFieldFinished()    */

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main_botom_bar, menu)
    }

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        /*context.setSupportActionBar(binding.bottomBarMain)
        setHasOptionsMenu(true)*/

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.send_main_bottom_bar) {
           /* rememberDate()*/
            /*rememberWorkedOutAmount()*/
            checkFieldsCompleteness()
            /*goToSaveFragment(activity?.supportFragmentManager)*/
        }
        if (item.itemId == R.id.refresh) {
           /* sendDataToServer()*/

        }
        if (item.itemId == R.id.order_list) {

            val manager = activity?.supportFragmentManager
            val listItem=ListItem("","","","","","")
            /*showOrHideOrdersList()*/

            /*makeDetails(manager,listItem)*/

        }


        return super.onOptionsItemSelected(item)
    }

    private suspend fun showImage(questionNumber:String): Int {
        //это заглушка - переделать метод

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


    private fun checkFieldsCompleteness() {
        /* if (viewModel.checkCompleteness(
                 GlobalConstAndVars.LIST_OF_ITEMS_FOR_FIRST_AND_SECOND_SCREENS,
                 GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS,
                 GlobalConstAndVars.DATE_OF_ORDER,
                 GlobalConstAndVars.WORKED_OUT
             ) == "Данные наряда заполнены не полностью"
         ) {
             toast("Данные наряда заполнены не полностью")
         } else {
             viewModel.putDataToResultDB(GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS)
             toast("данные записаны успешно")
             val workedOut = binding.inputEditTextWorkedOut
             sendDataToServer()
             setDefaultValuesForTheGlobalVars(workedOut)


         }*/
    }

    private fun updateSearch() {
        /*   val etSearchBar=binding.inputEditText
           val s = etSearchBar.text
           if (s?.length == 0) {
               adapter.setListItem(viewModel.convertArrayListItemToMainList(SearchItemStorage.list))
           } else {
               adapter.setListItem( viewModel.convertArrayListItemToMainList(SearchItemStorage.list).filter {
                    it.name.contains(s.toString(), true)
               } )
           }*/

    }

    private fun renderList(data: AppState) {
        when (data) {
            is AppState.SuccessTickets -> {
                ticketsAdapter.setListItem(data.tickets)

                /*  SearchItemStorage.list=viewModel.convertMainListToArrayListItem(data.listItem)*/
            }
            is AppState.SuccessQuestions -> {
                questionsAdapter.setListItem(data.questions)

            }

            is AppState.Loading -> {
            }
            is AppState.Error -> {
                toast(data.error.message)

            }

        }

    }
    private fun renderQuestionsList(data: AppState) {
        when (data) {
            is AppState.Success -> {
                questionsAdapter.setListItem(data.listItem)
                /*  SearchItemStorage.list=viewModel.convertMainListToArrayListItem(data.listItem)*/
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                toast(data.error.message)

            }

        }

    }



    private fun isDataUploadedToServer(data: AppState) {
        when (data) {
            is AppState.Success -> {
                toast("Выгрузка прошла успешно")
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                toast("Данные не загружены:${data.error.message}")


            }

        }

    }

    interface OnItemViewClickListener {
        fun onItemViewClick(listItem: ListItem)
    }

    private fun Fragment.toast(string: String?) {
        fun handleError() {}
        val fragmentCoroutineScope = CoroutineScope(
            Dispatchers.Main+ SupervisorJob() + CoroutineExceptionHandler { _, _ -> handleError() })

        fragmentCoroutineScope.launch { Toast.makeText(context, string, Toast.LENGTH_LONG).
        show() }

    }
    private fun addZeroToMonthAndDay(dayOrMonth:Int):String{
        return if (dayOrMonth <10) {
            "0$dayOrMonth"

        } else{
            dayOrMonth.toString()
        }

    }
    private fun hideUnnecessaryFields(){
        /*   if (count!= KEY_FOR_INFLATE_MAIN_LIST) {
               //второй экран
               binding.inputEditTextDate.isGone=true
               binding.bottomBarMain.isGone=true
               binding.inputLayout.isGone=false
               binding.inputEditTextWorkedOut.isGone=true
               binding.inputDateLayout.endIconMode=TextInputLayout.END_ICON_NONE
               val params = binding.mainFragmentRecyclerView.layoutParams as ConstraintLayout.LayoutParams
               params.topToBottom=binding.inputLayout.id
               params.matchConstraintPercentHeight= 0.89F

           } else {
               //первый экран
               binding.inputDateLayout.isGone=false
               binding.inputLayout.isGone=true
               binding.bottomBarMain.isGone=false
               binding.inputEditTextWorkedOut.isGone=false

           }*/
    }

    private fun createCalendar() {

        /*  val textView = binding.inputEditTextDate
          val c = Calendar.getInstance()
          val year = c.get(Calendar.YEAR)
          val monthFromCalendar = c.get(Calendar.MONTH)
          val day = c.get(Calendar.DAY_OF_MONTH)
          textView.setText(GlobalConstAndVars.DATE_OF_ORDER)
          input_date_layout.setEndIconOnClickListener {
              val dpd = DatePickerDialog(requireContext(), { _, year, _, dayOfMonth ->
                  val month = monthFromCalendar + 1
                  GlobalConstAndVars.DATE_OF_ORDER =
                      "$year.${addZeroToMonthAndDay(month)}.${addZeroToMonthAndDay(dayOfMonth)}"
                  textView.setText(GlobalConstAndVars.DATE_OF_ORDER)
              }, year, monthFromCalendar, day)
              dpd.show()
          }*/
    }

    private fun setWorkedOutFieldBehavior() {
        /* val workedOut = binding.inputEditTextWorkedOut
         workedOut.setText(GlobalConstAndVars.WORKED_OUT)
         workedOut.setOnClickListener {
             GlobalConstAndVars.WORKED_OUT = workedOut.text.toString()
             workedOut.setText(GlobalConstAndVars.WORKED_OUT)
         }*/

    }
    private fun isEditingWorkedOutFieldFinished() {
        /*    binding.inputEditTextWorkedOut.setOnFocusChangeListener { _: View, b: Boolean ->
                if (!b) {
                    GlobalConstAndVars.WORKED_OUT = binding.inputEditTextWorkedOut.text.toString()
                    binding.inputEditTextWorkedOut.setText(GlobalConstAndVars.WORKED_OUT)
                }
            }*/
    }

    private fun launchSearchBarListener(){
        val s="2"
        val ee=""
        /*    val etSearchBar=binding.inputEditText
            etSearchBar.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    updateSearch()
                }
            })*/
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




