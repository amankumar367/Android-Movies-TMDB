package com.dev.aman.movies_tmdb.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.dev.aman.movies_tmdb.BaseActivity
import com.dev.aman.movies_tmdb.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
        setUpActionBar()
        setNavControllerListener()
        setUpBottomNavigation()
        setUpLeftNavigation()

    }

    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_screen, R.id.trailer_screen, R.id.progress_screen),
            drawer_layout)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setNavControllerListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
            Log.d(TAG, " >>> Navigated to $dest")
        }
    }

    private fun setUpBottomNavigation() {
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setUpLeftNavigation() {
        navigationView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return menuItem.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(menuItem)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object{
        private var TAG : String = "MainActivity"
    }

}
