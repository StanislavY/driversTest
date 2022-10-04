package com.example.order.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.order.R
import com.example.order.app.domain.usecase.*
import com.example.order.viewModel.LoadingViewModel
import com.example.order.databinding.LoadingFragmentBinding
import kotlinx.coroutines.*

class LoadingFragment:Fragment() {
    private var _binding:LoadingFragmentBinding?=null
    private val binding get()=_binding!!
    private val viewModel:LoadingViewModel by lazy { ViewModelProvider(this).get(LoadingViewModel::class.java)

    }

    private val fireBase: FireBaseCase =FirebaseCaseImpl()
    private val load: LoadDataFromDBCase = LoadDataFromDBCaseImpl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= LoadingFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadinglayout.show()
        appCoroutineScope.launch{
            load.executeDownloadingDataFromFireBaseToLocalDB(fireBase.executeGettingDataFromFirebase("test"))
            viewModel.getGlobalLIst()
            goToMainList(activity?.supportFragmentManager)
        }

    }
    private fun goToMainList(
        manager: FragmentManager?,

    ) {
        manager?.beginTransaction()?.replace(R.id.container, MainFragment.newInstance())
            ?.addToBackStack("")?.commitAllowingStateLoss()
    }

    private val appCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, _ ->
            handleError()
        })

    private fun handleError() {}
    companion object {
        fun newInstance() = LoadingFragment()

        }
    }






