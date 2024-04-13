package com.ra.bkuang.features.analytics.di

import com.ra.bkuang.features.analytics.presentation.adapter.AnalyticListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object AnalyticFragmentModule {
  @Provides
  fun provideAnalyticListAdapter(): AnalyticListAdapter = AnalyticListAdapter()
}