package com.ra.bkuang.features.account.di

import com.ra.bkuang.features.account.presentation.adapter.RvAccountAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object AccountFragmentModule {

  @Provides
  fun bindRvAccountAdapter(): RvAccountAdapter = RvAccountAdapter()

}