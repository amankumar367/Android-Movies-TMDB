package com.dev.aman.movies_tmdb.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.aman.movies_tmdb.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewPager()
        setBottomViewListener()

    }

    private fun setViewPager() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
    }

    private fun setBottomViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> { viewPager.currentItem = 0 }
                R.id.trailer -> { viewPager.currentItem = 1 }
                R.id.progress -> { viewPager.currentItem = 2 }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerListener() {
        viewPager.setOnTouchListener { _, _ -> true }
    }

}
