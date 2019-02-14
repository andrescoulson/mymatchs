package com.andrescoulson.mymatchs.mvp.result

import com.andrescoulson.mymatchs.model.Match

interface IResultView {

    fun succesResultados(resuls:List<Match>)
    fun errResultados()
}