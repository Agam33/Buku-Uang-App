package com.ra.bkuang.features.account.di

import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoney
import com.ra.bkuang.features.account.domain.usecase.DeleteAkun
import com.ra.bkuang.features.account.domain.usecase.FindAkunById
import com.ra.bkuang.features.account.domain.usecase.FindAllAkun
import com.ra.bkuang.features.account.domain.usecase.SaveAkun
import com.ra.bkuang.features.account.domain.usecase.UpdateAkun
import com.ra.bkuang.features.account.domain.usecase.impl.AkunOverallMoneyImpl
import com.ra.bkuang.features.account.domain.usecase.impl.DeleteAkunImpl
import com.ra.bkuang.features.account.domain.usecase.impl.FindAkunByIdImpl
import com.ra.bkuang.features.account.domain.usecase.impl.FindAllAkunImpl
import com.ra.bkuang.features.account.domain.usecase.impl.SaveAkunImpl
import com.ra.bkuang.features.account.domain.usecase.impl.UpdateAkunImpl
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
  fun bindGetOverallMoney(getOverallMoney: AkunOverallMoneyImpl): AkunOverallMoney

  @Binds
  @Singleton
  fun bindSaveTabunganUseCase(saveAkunImpl: SaveAkunImpl): SaveAkun

  @Binds
  @Singleton
  fun bindDeleteTabunganUseCase(deleteAkunImpl: DeleteAkunImpl): DeleteAkun

  @Binds
  @Singleton
  fun bindUpdateTabunganUseCase(updateAkunImpl: UpdateAkunImpl): UpdateAkun

  @Binds
  @Singleton
  fun bindFindAllTabunganUseCase(findAllAkunImpl: FindAllAkunImpl): FindAllAkun

  @Binds
  @Singleton
  fun bindFindTabunganByIdUseCase(findAkunByIdImpl: FindAkunByIdImpl): FindAkunById
}