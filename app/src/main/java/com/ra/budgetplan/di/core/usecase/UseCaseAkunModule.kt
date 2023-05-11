package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.akun.DeleteAkun
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.domain.usecase.akun.FindAkunById
import com.ra.budgetplan.domain.usecase.akun.SaveAkun
import com.ra.budgetplan.domain.usecase.akun.UpdateAkun
import com.ra.budgetplan.domain.usecase.akun.impl.DeleteAkunImpl
import com.ra.budgetplan.domain.usecase.akun.impl.FindAllAkunImpl
import com.ra.budgetplan.domain.usecase.akun.impl.FindAkunByIdImpl
import com.ra.budgetplan.domain.usecase.akun.impl.SaveAkunImpl
import com.ra.budgetplan.domain.usecase.akun.impl.UpdateAkunImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseAkunModule {

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