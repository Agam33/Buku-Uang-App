package com.ra.budgetplan.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  private val navController: NavController by lazy { Navigation.findNavController(this@MainActivity, R.id.fragment_container) }

  private lateinit var drawerToggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupActionBar()
    setupNavigationDrawer()
    setupOnBackPressedDispatcher()
  }

  private fun setupNavigationDrawer() {
    NavigationUI.setupWithNavController(binding.navigationDrawerView, navController)
    drawerToggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayout, R.string.txt_open, R.string.txt_close)
    binding.drawerLayout.addDrawerListener(drawerToggle)
    drawerToggle.syncState()

    binding.navigationDrawerView.setNavigationItemSelectedListener {item ->

      when(item.itemId) {
        R.id.menu_account -> {
          navController.popBackStack()
          navController.navigate(R.id.accountFragment)
        }
        R.id.menu_analytics -> {
          navController.popBackStack()
          navController.navigate(R.id.analyticFragment)
        }
        R.id.menu_budget -> {
          navController.popBackStack()
          navController.navigate(R.id.budgetFragment)
        }
        R.id.menu_category -> {
          navController.popBackStack()
          navController.navigate(R.id.categoryFragment)
        }
        R.id.menu_transaction -> {
          navController.popBackStack()
          navController.navigate(R.id.transactionFragment)
        }
      }
      binding.drawerLayout.closeDrawer(GravityCompat.START)
      true
    }
  }

  private fun setupActionBar() {
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.elevation = 0F
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if(drawerToggle.onOptionsItemSelected(item)) return true
    return super.onOptionsItemSelected(item)
  }

  private fun setupOnBackPressedDispatcher() {
    val callback = object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
          binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
          finish()
        }
      }
    }
    onBackPressedDispatcher.addCallback(this, callback)
  }
}