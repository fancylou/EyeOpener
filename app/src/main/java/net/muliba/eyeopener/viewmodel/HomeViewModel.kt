package net.muliba.eyeopener.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import net.muliba.eyeopener.EyeOpenerApplication
import net.muliba.eyeopener.repository.HomeRepository
import net.muliba.eyeopener.repository.api.RetrofitClient
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem

/**
 * Created by fancyLou on 11/04/2018.
 */


class HomeViewModel : AndroidViewModel {

    private val homeRepository: HomeRepository

    constructor(application: Application): super(application) {
        val homeItemDao = (application as EyeOpenerApplication).database.homeItemDao()
        val api = RetrofitClient.getInstance(application).apiService()
        homeRepository = HomeRepository(api, homeItemDao)
    }

    private val TAG = "HomeViewModel"

    var homeItemList: LiveData<List<RoomHomeItem>>? = null

    fun loadHomeItemList(createDate: String) {
        Log.i(TAG, "createDate: $createDate")
        if (homeItemList == null) {
            homeItemList = homeRepository.getHomeItemList(createDate)
        }
    }
}