package com.ra.budgetplan.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.ra.budgetplan.R
import com.ra.budgetplan.base.BaseActivity
import com.ra.budgetplan.data.local.preferences.UserSettingPref
import com.ra.budgetplan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private lateinit var navController: NavController

  private lateinit var drawerToggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    navController = Navigation.findNavController(this@MainActivity, R.id.fragment_container)
    setupActionBar()
    setupNavigationDrawer()
    setupOnBackPressedDispatcher()
  }

  private fun setupNavigationDrawer() {
    NavigationUI.setupWithNavController(binding.navigationDrawerView, navController)
    drawerToggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayout, R.string.txt_open, R.string.txt_close)
    binding.drawerLayout.addDrawerListener(drawerToggle)
    drawerToggle.syncState()

    binding.navigationDrawerView.setCheckedItem(R.id.transactionFragment)

    binding.navigationDrawerView.setNavigationItemSelectedListener { item ->
      navController.popBackStack()
      when(item.itemId) {
        R.id.menu_account -> {
          navController.navigate(R.id.accountFragment)
        }
        R.id.menu_analytics -> {
          navController.navigate(R.id.analyticFragment)
        }
        R.id.menu_budget -> {
          navController.navigate(R.id.budgetFragment)
        }
        R.id.menu_category -> {
          navController.navigate(R.id.categoryFragment)
        }
        R.id.menu_transaction -> {
          navController.navigate(R.id.transactionFragment)
        }
        R.id.menu_backup_and_restore -> {
          navController.navigate(R.id.backupRestoreFragment)
        }
        R.id.menu_debt -> {
          navController.navigate(R.id.debtFragment)
        }
      }
      binding.drawerLayout.closeDrawer(GravityCompat.START)
      true
    }
  }

  private fun setupActionBar() {
    setSupportActionBar(binding.toolbar)
    supportActionBar?.title = ""
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.elevation = 0F
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if(drawerToggle.onOptionsItemSelected(item)) return true
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    return true
  }

  private fun setupOnBackPressedDispatcher() {
    val callback = object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
          binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
          Snackbar.make(binding.root, resources.getString(R.string.msg_to_close_app), Snackbar.LENGTH_SHORT)
            .setAction(resources.getString(R.string.txt_yes)) {
              finish()
            }
            .show()
        }
      }
    }
    onBackPressedDispatcher.addCallback(this, callback)
  }
}