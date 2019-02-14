package com.andrescoulson.mymatchs.mvp.fixture

import com.andrescoulson.mymatchs.model.Match

interface IFixtureView {

    fun succesFixture(fixture:List<Match>)
    fun erroFixture()
}