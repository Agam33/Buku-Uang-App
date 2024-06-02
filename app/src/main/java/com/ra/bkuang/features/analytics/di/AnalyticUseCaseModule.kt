package com.ra.bkuang.features.analytics.di

import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalyticsUseCase
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticListUseCase
import com.ra.bkuang.features.analytics.domain.usecase.impl.DetailAnalyticsUseCaseImpl
import com.ra.bkuang.features.analytics.domain.usecase.impl.ShowAnalyticListUseCaseImpl
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
  fun bindShowAnalyticList(showAnalyticListImpl: ShowAnalyticListUseCaseImpl): ShowAnalyticListUseCase

  @Binds
  @Singleton
  fun bindDetailAnalytics(detailAnalyticsImpl: DetailAnalyticsUseCaseImpl): DetailAnalyticsUseCase
}