package com.ra.bkuang.di.core.usecase

import com.ra.bkuang.domain.usecase.analisis.DetailAnalytics
import com.ra.bkuang.domain.usecase.analisis.ShowAnalyticList
import com.ra.bkuang.domain.usecase.analisis.impl.DetailAnalyticsImpl
import com.ra.bkuang.domain.usecase.analisis.impl.ShowAnalyticListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseAnalyticModule {

  @Binds
  @Singleton
  fun bindShowAnalyticList(showAnalyticListImpl: ShowAnalyticListImpl): ShowAnalyticList

  @Binds
  @Singleton
  fun bindDetailAnalytics(detailAnalyticsImpl: DetailAnalyticsImpl): DetailAnalytics
}