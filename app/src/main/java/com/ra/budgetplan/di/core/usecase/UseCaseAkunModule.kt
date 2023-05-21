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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseAkunModule {

  @Binds
  fun bindSaveTabunganUseCase(saveAkunImpl: SaveAkunImpl): SaveAkun

  @Binds
  fun bindDeleteTabunganUseCase(deleteAkunImpl: DeleteAkunImpl): DeleteAkun

  @Binds
  fun bindUpdateTabunganUseCase(updateAkunImpl: UpdateAkunImpl): UpdateAkun

  @Binds
  fun bindFindAllTabunganUseCase(findAllAkunImpl: FindAllAkunImpl): FindAllAkun

  @Binds
  fun bindFindTabunganByIdUseCase(findAkunByIdImpl: FindAkunByIdImpl): FindAkunById
}