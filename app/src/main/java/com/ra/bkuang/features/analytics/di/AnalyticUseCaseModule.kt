package com.ra.bkuang.features.analytics.di

import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalytics
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticList
import com.ra.bkuang.features.analytics.domain.usecase.impl.DetailAnalyticsImpl
import com.ra.bkuang.features.analytics.domain.usecase.impl.ShowAnalyticListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AnalyticUseCaseModule {

  @Binds
  @Singleton
  fun bindShowAnalyticList(showAnalyticListImpl: ShowAnalyticListImpl): ShowAnalyticList

  @Binds
  @Singleton
  fun bindDetailAnalytics(detailAnalyticsImpl: DetailAnalyticsImpl): DetailAnalytics
}