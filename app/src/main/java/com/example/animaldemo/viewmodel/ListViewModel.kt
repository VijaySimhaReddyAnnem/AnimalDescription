package com.example.animaldemo.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animaldemo.model.Animal
import com.example.animaldemo.model.AnimalApiService
import com.example.animaldemo.model.ApiKey
import com.example.animaldemo.utils.SharedPreferenceHelper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application):AndroidViewModel(application) {

    private val apiService=AnimalApiService()
    private val disposible=CompositeDisposable()

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy{ MutableLiveData<Boolean>()}
    val loading by  lazy { MutableLiveData<Boolean>()}

    private  val prefs=SharedPreferenceHelper(getApplication())
    private var invalidApiKey=false
    fun refresh(){
        loading.value=true
        invalidApiKey=false
        val key=prefs.getApiKey()
        if(key.isNullOrEmpty()){
            getKey()
        }
        else getAnimals(key)

    }
    fun hardRefresh(){
        loading.value=true
        getKey()
    }
    fun getKey(){
        disposible.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if(key.key.isNullOrEmpty()){
                            loadError.value=true
                            loading.value=false
                        }
                        else{
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value=false
                        loadError.value=true
                    }

                })
        )
    }
    private fun getAnimals(key:String){
                disposible.add(
                    apiService.getAnimals(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object:DisposableSingleObserver<List<Animal>>(){
                            override fun onSuccess(list: List<Animal>) {
                                if(!list.isEmpty()){
                                    loading.value=false
                                    loadError.value=false
                                    animals.value=list
                                }
                                else{
                                    loading.value=false
                                    loadError.value=true
                                    animals.value=null
                                }
                            }
                            override fun onError(e: Throwable) {
                                if(!invalidApiKey){
                                    invalidApiKey=true
                                    getKey()
                                }
                                else{
                                    e.printStackTrace()
                                    loading.value=false
                                    loadError.value=true
                                    animals.value=null
                                }

                            }

                        })
                )

    }

    override fun onCleared() {
        super.onCleared()
        disposible.clear()
    }
}
