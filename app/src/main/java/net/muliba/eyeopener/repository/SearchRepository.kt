package net.muliba.eyeopener.repository

import io.reactivex.Observable
import net.muliba.eyeopener.repository.api.ApiService
import net.muliba.eyeopener.repository.api.bean.SearchBean

/**
 * Created by fancyLou on 13/04/2018.
 */


class SearchRepository(val apiService: ApiService) {
    private val TAG = "SearchRepository"


    fun search(query: String, start:Int): Observable<SearchBean>  = apiService.getSearchData(10, query, start)

}