package com.andrescoulson.mymatchs

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.andrescoulson.mymatchs.adapter.MainFragmentPageAdapter

class MainActivity : AppCompatActivity() {

    lateinit var bindind: com.andrescoulson.mymatchs.databinding.ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(bindind.toolbar)
        bindind.container.adapter = MainFragmentPageAdapter(this, supportFragmentManager)
        bindind.tabs.setupWithViewPager(bindind.container)
    }
}
