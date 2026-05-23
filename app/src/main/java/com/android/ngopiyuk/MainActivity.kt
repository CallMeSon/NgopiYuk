package com.android.ngopiyuk

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar sebagai ActionBar
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Setup DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Inisialisasi Navigation Component
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Top-level destinations (tampil hamburger icon, bukan back arrow)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.pemesananFragment,
                R.id.profileFragment,
                R.id.favoritesFragment,
                R.id.settingsFragment,
                R.id.aboutFragment
            ),
            drawerLayout
        )

        // Hubungkan Toolbar dengan NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Hubungkan NavigationView (Drawer) dengan NavController
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)

        // Hubungkan BottomNavigationView dengan NavController
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        // Sembunyikan/tampilkan BottomNav berdasarkan destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.dashboardFragment,
                R.id.pemesananFragment,
                R.id.profileFragment -> {
                    bottomNav.visibility = android.view.View.VISIBLE
                }
                else -> {
                    bottomNav.visibility = android.view.View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        return when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
                true
            }
            R.id.action_about -> {
                navController.navigate(R.id.aboutFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
