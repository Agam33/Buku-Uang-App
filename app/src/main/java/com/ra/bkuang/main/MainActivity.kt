package com.ra.bkuang.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.Extension.appOnBackPressed
import com.ra.bkuang.databinding.ActivityMainBinding
import com.ra.bkuang.features.account.presentation.AccountFragment
import com.ra.bkuang.features.analytics.presentation.AnalyticFragment
import com.ra.bkuang.features.backuprestore.presentation.BackupRestoreFragment
import com.ra.bkuang.features.budget.presentation.fragment.BudgetFragment
import com.ra.bkuang.features.category.presentation.CategoryFragment
import com.ra.bkuang.features.debt.presentation.DebtFragment
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

  private lateinit var drawerToggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActionBar()
    setupDrawer()
    setupOnBackPressedDispatcher()

    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, TransactionFragment(), "TransactionFragment")
      .commit()

    binding.navigationDrawerView.setCheckedItem(R.id.menu_transaction)
  }

  private fun setupDrawer() {
    drawerToggle = ActionBarDrawerToggle(
      this@MainActivity,
      binding.drawerLayout,
      R.string.txt_open, R.string.txt_close
    )

    binding.drawerLayout.addDrawerListener(drawerToggle)
    drawerToggle.syncState()

    binding.navigationDrawerView.setNavigationItemSelectedListener { item ->
      when(item.itemId) {
        R.id.menu_account -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AccountFragment(), "AccountFragment")
            .commit()
        }
        R.id.menu_analytics -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AnalyticFragment(), "AnalyticFragment")
            .commit()
        }
        R.id.menu_budget -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BudgetFragment(), "BudgetFragment")
            .commit()
        }
        R.id.menu_category -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoryFragment(), "CategoryFragment")
            .commit()
        }
        R.id.menu_transaction -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TransactionFragment(), "TransactionFragment")
            .commit()
        }
        R.id.menu_backup_and_restore -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BackupRestoreFragment(), "BackupRestoreFragment")
            .commit()
        }
        R.id.menu_debt -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DebtFragment(), "DebtFragment")
            .commit()
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
   appOnBackPressed {
      if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
      } else {
        Snackbar.make(
          binding.root,
          resources.getString(R.string.msg_to_close_app),
          Snackbar.LENGTH_SHORT
        )
          .setAction(resources.getString(R.string.txt_yes)) {
            finish()
          }
          .show()
      }
    }
  }
}