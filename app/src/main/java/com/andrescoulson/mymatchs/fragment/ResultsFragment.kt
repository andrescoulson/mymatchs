package com.andrescoulson.mymatchs.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Toast

import com.andrescoulson.mymatchs.R
import com.andrescoulson.mymatchs.adapter.FixtureRecyclerAdapter
import com.andrescoulson.mymatchs.adapter.ResultsRecyclerAdapter
import com.andrescoulson.mymatchs.config.Util
import com.andrescoulson.mymatchs.databinding.FragmentResultsBinding
import com.andrescoulson.mymatchs.model.Match
import com.andrescoulson.mymatchs.mvp.result.IResultPresenter
import com.andrescoulson.mymatchs.mvp.result.IResultView
import com.andrescoulson.mymatchs.mvp.result.ResultPresenter
import java.util.*


class ResultsFragment : Fragment(), IResultView, SearchView.OnQueryTextListener {


    lateinit var presenter: IResultPresenter

    lateinit var binding: FragmentResultsBinding
    var listMatch = listOf<Match>()
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerResults.setHasFixedSize(true)
        binding.recyclerResults.layoutManager = LinearLayoutManager(context)
        binding.recyclerResults.adapter = ResultsRecyclerAdapter()
        binding.recyclerResults.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        presenter = ResultPresenter(this)
        fetResult()
    }

    private fun fetResult() {
        presenter.getResult()
    }


    override fun succesResultados(resuls: List<Match>) {
        Collections.sort(
            resuls
        ) { t, t1 -> Util.getDate(t.date!!).compareTo(Util.getDate(t1.date!!)) }
        listMatch = resuls
        (binding.recyclerResults.adapter as ResultsRecyclerAdapter).setData(Util.getListData(resuls))
    }

    override fun errResultados() {
        Toast.makeText(context, getString(R.string.err_fetch), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        val searcitem = menu?.findItem(R.id.action_search)
        searchView = searcitem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Search"
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        var listemp = arrayListOf<Match>()
        for (i in listMatch) {
            if (Util.containMont(i, newText!!.toLowerCase()))
                listemp.add(i)
        }
        if (!listemp.isEmpty())
            (binding.recyclerResults.adapter as ResultsRecyclerAdapter).setData(Util.getListData(listemp))
        return true
    }
}
