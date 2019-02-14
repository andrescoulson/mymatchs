package com.andrescoulson.mymatchs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.os.Build
import android.content.Intent





class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT < 16) {
            try {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            try {
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()


    }
}
