package com.korilin.sunnyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowStatusBarSetting()
        setContentView(R.layout.activity_main)
    }
}