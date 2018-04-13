package net.muliba.eyeopener.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.muliba.eyeopener.repository.SearchRepository
import net.muliba.eyeopener.repository.api.RetrofitClient
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem

/**
 * Created by fancyLou on 13/04/2018.
 */


class ResultActivityViewModel : AndroidViewModel {


    private val TAG = "ResultActivityViewModel"
    private val searchRepository: SearchRepository

    /**
     * 查询的值
     */
    val mQuery: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    /**
     * 查询结果集
     */
    val mResultList: LiveData<List<RoomHomeItem>> = Transformations.switchMap(mQuery, { input ->
        Log.i(TAG, "input : $input")
        val data = MutableLiveData<List<RoomHomeItem>>()
        refreshItemListLiveData(input, data)
        data
    })

    constructor(application: Application) : super(application) {
        val api = RetrofitClient.getInstance(application).apiService()
        searchRepository = SearchRepository(api)

    }

    fun search(query: String) {
        Log.i(TAG, "query: $query")
        mQuery.value = query
    }


    private fun refreshItemListLiveData(input: String, data: MutableLiveData<List<RoomHomeItem>>) {
        searchRepository.search(input, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    Log.i(TAG, "获取网络数据返回， ${result.count}")
                    val list = result.itemList
                    if (list!=null) {
                        data.value = list
                    }
                })
    }


}