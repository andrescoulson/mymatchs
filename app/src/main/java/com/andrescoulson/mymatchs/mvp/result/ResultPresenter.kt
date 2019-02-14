package com.andrescoulson.mymatchs.mvp.result

import com.andrescoulson.mymatchs.model.Match
import com.andrescoulson.mymatchs.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResultPresenter(val iResultView: IResultView) : IResultPresenter {
    override fun getResult() {
        val disposable = ApiClient.apiClient.getResults()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::succes, this::error)
    }

    private fun succes(result: List<Match>?) {
        if (result?.isEmpty()!!)
            iResultView.errResultados()
        else
            iResultView.succesResultados(result)
    }

    private fun error(throwable: Throwable) {
        iResultView.errResultados()
    }
}