package com.dev.aman.movies_tmdb.ui.mainActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.extentions.showToast
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBar()
        setViewPager()
        setBottomViewListener()
        setLeftNavigationView()

    }

    private fun setUpActionBar() {
        toolbar.title = "Home"
        toolbar.setTitleTextColor(resources.getColor(R.color.white_10))
        toolbar.navigationIcon
        setSupportActionBar(toolbar)

        val drawerToggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close)

        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
    }

    private fun setViewPager() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        viewPager.setOnTouchListener { _, _ -> true }
    }

    private fun setBottomViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bn_home -> { viewPager.currentItem = 0 }
                R.id.bn_trailer -> { viewPager.currentItem = 1 }
                R.id.bn_progress -> { viewPager.currentItem = 2 }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setLeftNavigationView() {
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.ln_home                -> { showToast("Home") }
            R.id.ln_movies              -> { showToast("Movies") }
            R.id.ln_tv_shows            -> { showToast("TV Shows") }
            R.id.ln_dicover             -> { showToast("Discover") }
            R.id.ln_popular_people      -> { showToast("Popular People") }
            R.id.ln_watchlist           -> { showToast("Watchlist") }
            R.id.ln_history             -> { showToast("History") }
            R.id.ln_collection          -> { showToast("Collection") }
            R.id.ln_ratings             -> { showToast("Ratings") }
            R.id.ln_personal_list       -> { showToast("Personal List") }
            R.id.ln_reminders           -> { showToast("Reminders") }
            R.id.ln_hidden_items        -> { showToast("Hidden Items") }
            R.id.ln_setting             -> { showToast("Settings") }
            R.id.ln_helps_and_feedback  -> { showToast("Help & feedback") }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object{
        private var TAG : String = MainActivity::class.java.simpleName
    }

}
