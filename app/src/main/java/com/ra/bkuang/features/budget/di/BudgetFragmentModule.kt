package com.ra.bkuang.features.budget.di

import com.ra.bkuang.features.budget.presentation.adapter.BudgetAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object BudgetFragmentModule {
  @Provides
  fun provideBudgetAdapter(): BudgetAdapter = BudgetAdapter()
}