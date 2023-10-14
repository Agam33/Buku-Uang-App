package com.ra.bkuang.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.base.BaseActivity
import com.ra.bkuang.data.local.preferences.UserSettingPref
import com.ra.bkuang.databinding.ActivityMainBinding
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
    val option = NavOptions.Builder().apply {
      setLaunchSingleTop(true)
      setEnterAnim(R.animator.zoom_out_anim)
    }.build()
    binding.navigationDrawerView.setNavigationItemSelectedListener { item ->
      navController.popBackStack()
      when(item.itemId) {
        R.id.menu_account -> {
          navController.navigate(R.id.accountFragment, Bundle(), option)
        }
        R.id.menu_analytics -> {
          navController.navigate(R.id.analyticFragment, Bundle(), option)
        }
        R.id.menu_budget -> {
          navController.navigate(R.id.budgetFragment, Bundle(), option)
        }
        R.id.menu_category -> {
          navController.navigate(R.id.categoryFragment, Bundle(), option)
        }
        R.id.menu_transaction -> {
          navController.navigate(R.id.transactionFragment, Bundle(), option)
        }
        R.id.menu_backup_and_restore -> {
          navController.navigate(R.id.backupRestoreFragment, Bundle(), option)
        }
        R.id.menu_debt -> {
          navController.navigate(R.id.debtFragment, Bundle(), option)
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