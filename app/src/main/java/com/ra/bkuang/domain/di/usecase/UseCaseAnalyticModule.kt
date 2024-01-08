package com.ra.bkuang.domain.di.usecase

import com.ra.bkuang.domain.usecase.analisis.DetailAnalytics
import com.ra.bkuang.domain.usecase.analisis.ShowAnalyticList
import com.ra.bkuang.domain.usecase.analisis.impl.DetailAnalyticsImpl
import com.ra.bkuang.domain.usecase.analisis.impl.ShowAnalyticListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseAnalyticModule {

  @Binds
  fun bindShowAnalyticList(showAnalyticListImpl: ShowAnalyticListImpl): ShowAnalyticList

  @Binds
  fun bindDetailAnalytics(detailAnalyticsImpl: DetailAnalyticsImpl): DetailAnalytics
}