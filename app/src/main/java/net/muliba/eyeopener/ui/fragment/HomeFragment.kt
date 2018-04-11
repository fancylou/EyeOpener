package net.muliba.eyeopener.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import net.muliba.accounting.utils.ext.DateHelper
import net.muliba.eyeopener.R
import net.muliba.eyeopener.adapter.HomeDailyListAdapter
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem
import net.muliba.eyeopener.viewmodel.HomeViewModel

/**
 * Created by fancyLou on 11/04/2018.
 */


class HomeFragment : Fragment() {

    private val TAG = " HomeFragment"

    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    private val adapter: HomeDailyListAdapter by lazy { HomeDailyListAdapter(activity) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadHomeItemList(DateHelper.today())
        viewModel.homeItemList?.observe(this, Observer<List<RoomHomeItem>>{list ->
            if (list!=null) {
                Log.i(TAG, "homeitemList observe..................${list?.size}")
                adapter.setList(list.toMutableList())
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_home_daily.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler_home_daily.adapter = adapter
    }
}