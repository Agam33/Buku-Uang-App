package com.ra.bkuang.features.account.di

import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoneyUseCase
import com.ra.bkuang.features.account.domain.usecase.DeleteAkunUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAkunByIdUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunWithFlowUseCase
import com.ra.bkuang.features.account.domain.usecase.SaveAkunUseCase
import com.ra.bkuang.features.account.domain.usecase.UpdateAkunUseCase
import com.ra.bkuang.features.account.domain.usecase.impl.AkunOverallMoneyUseCaseImpl
import com.ra.bkuang.features.account.domain.usecase.impl.DeleteAkunUseCaseImpl
import com.ra.bkuang.features.account.domain.usecase.impl.FindAkunByIdUseCaseImpl
import com.ra.bkuang.features.account.domain.usecase.impl.FindAllAkunUseCaseImpl
import com.ra.bkuang.features.account.domain.usecase.impl.FindAllAkunWithFlowUseCaseImpl
import com.ra.bkuang.features.account.domain.usecase.impl.SaveAkunUseCaseImpl
import com.ra.bkuang.features.account.domain.usecase.impl.UpdateAkunUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AkunUseCaseModule {

  @Binds
  @Singleton
  fun bindFindAllAkunWithFlow(findAllAkunWithFlow: FindAllAkunWithFlowUseCaseImpl): FindAllAkunWithFlowUseCase

  @Binds
  @Singleton
  fun bindGetOverallMoney(getOverallMoney: AkunOverallMoneyUseCaseImpl): AkunOverallMoneyUseCase

  @Binds
  @Singleton
  fun bindSaveTabunganUseCase(saveAkunImpl: SaveAkunUseCaseImpl): SaveAkunUseCase

  @Binds
  @Singleton
  fun bindDeleteTabunganUseCase(deleteAkunImpl: DeleteAkunUseCaseImpl): DeleteAkunUseCase

  @Binds
  @Singleton
  fun bindUpdateTabunganUseCase(updateAkunImpl: UpdateAkunUseCaseImpl): UpdateAkunUseCase

  @Binds
  @Singleton
  fun bindFindAllTabunganUseCase(findAllAkunImpl: FindAllAkunUseCaseImpl): FindAllAkunUseCase

  @Binds
  @Singleton
  fun bindFindTabunganByIdUseCase(findAkunByIdImpl: FindAkunByIdUseCaseImpl): FindAkunByIdUseCase
}