package com.andrescoulson.mymatchs.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Toast

import com.andrescoulson.mymatchs.R
import com.andrescoulson.mymatchs.adapter.FixtureRecyclerAdapter
import com.andrescoulson.mymatchs.config.Util
import com.andrescoulson.mymatchs.model.Data
import com.andrescoulson.mymatchs.model.Match
import com.andrescoulson.mymatchs.mvp.fixture.FixturePresenter
import com.andrescoulson.mymatchs.mvp.fixture.IFixturePresenter
import com.andrescoulson.mymatchs.mvp.fixture.IFixtureView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FixtureFragment : Fragment(), IFixtureView, SearchView.OnQueryTextListener {


    lateinit var binding: com.andrescoulson.mymatchs.databinding.FragmentFixtureBinding
    lateinit var presenter: IFixturePresenter
    var listDate = arrayListOf<Data>()
    var listMatch = listOf<Match>()
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fixture, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerFixture.setHasFixedSize(true)
        binding.recyclerFixture.layoutManager = LinearLayoutManager(context)
        binding.recyclerFixture.adapter = FixtureRecyclerAdapter()
        presenter = FixturePresenter(this)
        fetchFixture()
    }

    private fun fetchFixture() {
        presenter.getFixture()
    }

    override fun succesFixture(fixture: List<Match>) {
        flowResponse(fixture)
    }

    private fun flowResponse(fixture: List<Match>) {
        listMatch = fixture
        (binding.recyclerFixture.adapter as FixtureRecyclerAdapter).setData(Util.getListData(fixture))

    }

    override fun erroFixture() {
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
            (binding.recyclerFixture.adapter as FixtureRecyclerAdapter).setData(Util.getListData(listemp))


        return true
    }
}
