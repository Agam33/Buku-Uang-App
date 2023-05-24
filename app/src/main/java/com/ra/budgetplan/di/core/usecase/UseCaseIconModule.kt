package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.icon.FindAllIcon
import com.ra.budgetplan.domain.usecase.icon.FindIconByCategory
import com.ra.budgetplan.domain.usecase.icon.impl.FindAllIconImpl
import com.ra.budgetplan.domain.usecase.icon.impl.FindIconByCategoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseIconModule {

  @Binds
  @Singleton
  fun bindFindAllIcon(findIconByCategoryImpl: FindIconByCategoryImpl): FindIconByCategory

  @Binds
  @Singleton
  fun bindFindIconByCategory(findAllIconImpl: FindAllIconImpl): FindAllIcon
}