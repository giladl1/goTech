package com.levins.junky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.levins.junky.ui.main.ItemActivityFragment

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ItemActivityFragment.newInstance())
                .commitNow()
        }
    }
}