package com.andrescoulson.mymatchs.mvp.fixture

import com.andrescoulson.mymatchs.model.Match
import com.andrescoulson.mymatchs.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class FixturePresenter(val iFixtureView: IFixtureView) : IFixturePresenter {
    override fun getFixture() {
        val disposable = ApiClient.apiClient.getFixture()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::succes, this::err)
    }

    private fun succes(list: List<Match>?) {
        if (!list?.isEmpty()!!)
            iFixtureView.succesFixture(list)
        else
            iFixtureView.erroFixture()

    }

    private fun err(throwable: Throwable) {
        iFixtureView.erroFixture()
    }
}