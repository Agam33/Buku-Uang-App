package com.ra.bkuang.features.category.di

import com.ra.bkuang.features.category.presentation.adapter.RvGroupCategoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object CategoryFragmentModule {

  @Provides
  fun provideRvCategoryAdapter(): RvGroupCategoryAdapter = RvGroupCategoryAdapter()
}