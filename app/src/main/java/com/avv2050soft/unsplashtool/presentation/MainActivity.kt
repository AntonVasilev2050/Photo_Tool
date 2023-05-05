package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_content_navigation) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView: BottomNavigationView = binding.bottomView
        navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.maxWidth = 600
        searchView.setOnCloseListener {
            navController.navigate(R.id.photosFragment)
            return@setOnCloseListener false
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    SearchPhotosViewModel.query = query
                }
                navController.navigate(R.id.searchPhotosFragment)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        menu.findItem(R.id.action_logout).isVisible = false
        menu.findItem(R.id.action_search).isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(menuItem, navController)
        return when (menuItem.itemId) {
            R.id.action_logout -> {
                navController.navigate(R.id.action_userFragment_to_logoutFragment)
                false
            }

            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

//    fun updateMenu() {
//        val menu = binding.toolbar.menu
//        val searchItem = menu.findItem(R.id.action_search)
//        val logoutItem = menu.findItem(R.id.action_logout)
//
//        when (supportFragmentManager.findFragmentById(R.id.container)?.tag) {
//            "PhotosFragment" -> {
//                searchItem.isVisible = false
//                logoutItem.isVisible = false
//            }
//
//            "CollectionsFragment" -> {
//                searchItem.isVisible = false
//                logoutItem.isVisible = false
//            }
//
//            "UserFragment" -> {
//                searchItem.isVisible = false
//                logoutItem.isVisible = true
//            }
//        }
//    }
}