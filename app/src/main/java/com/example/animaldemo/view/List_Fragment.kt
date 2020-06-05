package com.example.animaldemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.animaldemo.R
import com.example.animaldemo.model.Animal
import com.example.animaldemo.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class List_Fragment : Fragment() {

        private lateinit var viewModel:ListViewModel
        private  val listAdapter= AnimalListAdapter(arrayListOf())
         

        private val animalListDataObserver=Observer<List<Animal>>{list:List<Animal>->
            list?.let {
                listView.visibility=View.VISIBLE
                listAdapter.updateAnimalList(it)
            }

        }

       private val loadingLiveDataObserver=Observer<Boolean>{
           progressBar.visibility=if(it) View.VISIBLE else View.GONE
           if(it){
               errorMessage.visibility=View.GONE
               listView.visibility=View.GONE
           }
       }
    private val errorLiveDataObserver=Observer<Boolean>{
        errorMessage.visibility=if(it) View.VISIBLE else View.GONE
        if(it){
            listView.visibility=View.GONE
            progressBar.visibility=View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this,animalListDataObserver)
        viewModel.loadError.observe(this,errorLiveDataObserver)
        viewModel.loading.observe(this,loadingLiveDataObserver)

        viewModel.refresh()
        listView.apply {
            layoutManager=GridLayoutManager(context,2)
            adapter=listAdapter
        }


        refreshLayout.setOnRefreshListener {


                errorMessage.visibility=View.GONE
                progressBar.visibility=View.VISIBLE
                viewModel.hardRefresh()
                refreshLayout.isRefreshing=false


        }

    }
}
