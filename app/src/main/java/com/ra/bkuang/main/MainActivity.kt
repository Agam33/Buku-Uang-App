package com.ra.bkuang.main

import android.os.Bundle
import android.view.Menu
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.DrawerMenuToolbarListener
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
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main),
DrawerMenuToolbarListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupDrawer()
    setupOnBackPressedDispatcher()

    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, TransactionFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "TransactionFragment")
      .commit()

    binding.navigationDrawerView.setCheckedItem(R.id.menu_transaction)
  }

  private fun setupDrawer() {

    binding.navigationDrawerView.setNavigationItemSelectedListener { item ->
      when(item.itemId) {
        R.id.menu_account -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AccountFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "AccountFragment")
            .commit()
        }
        R.id.menu_analytics -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AnalyticFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "AnalyticFragment")
            .commit()
        }
        R.id.menu_budget -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BudgetFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "BudgetFragment")
            .commit()
        }
        R.id.menu_category -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoryFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "CategoryFragment")
            .commit()
        }
        R.id.menu_transaction -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TransactionFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "TransactionFragment")
            .commit()
        }
        R.id.menu_backup_and_restore -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BackupRestoreFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "BackupRestoreFragment")
            .commit()
        }
        R.id.menu_debt -> {
          supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DebtFragment().apply { drawerMenuToolbarListener = this@MainActivity }, "DebtFragment")
            .commit()
        }
      }
      binding.drawerLayout.closeDrawer(GravityCompat.START)
      true
    }
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

  override fun onDrawerMenuClicked() {
    binding.drawerLayout.openDrawer(GravityCompat.START)
  }
}