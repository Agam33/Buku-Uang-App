package com.ra.bkuang.domain.di.usecase

import com.ra.bkuang.domain.usecase.icon.FindAllIcon
import com.ra.bkuang.domain.usecase.icon.FindIconByCategory
import com.ra.bkuang.domain.usecase.icon.impl.FindAllIconImpl
import com.ra.bkuang.domain.usecase.icon.impl.FindIconByCategoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseIconModule {

  @Binds
  fun bindFindAllIcon(findIconByCategoryImpl: FindIconByCategoryImpl): FindIconByCategory

  @Binds
  fun bindFindIconByCategory(findAllIconImpl: FindAllIconImpl): FindAllIcon
}