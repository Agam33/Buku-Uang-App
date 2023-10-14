package com.ra.bkuang.di.core.usecase

import com.ra.bkuang.domain.usecase.akun.DeleteAkun
import com.ra.bkuang.domain.usecase.akun.FindAllAkun
import com.ra.bkuang.domain.usecase.akun.FindAkunById
import com.ra.bkuang.domain.usecase.akun.AkunOverallMoney
import com.ra.bkuang.domain.usecase.akun.SaveAkun
import com.ra.bkuang.domain.usecase.akun.UpdateAkun
import com.ra.bkuang.domain.usecase.akun.impl.DeleteAkunImpl
import com.ra.bkuang.domain.usecase.akun.impl.FindAllAkunImpl
import com.ra.bkuang.domain.usecase.akun.impl.FindAkunByIdImpl
import com.ra.bkuang.domain.usecase.akun.impl.AkunOverallMoneyImpl
import com.ra.bkuang.domain.usecase.akun.impl.SaveAkunImpl
import com.ra.bkuang.domain.usecase.akun.impl.UpdateAkunImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseAkunModule {

  @Binds
  fun bindGetOverallMoney(getOverallMoney: AkunOverallMoneyImpl): AkunOverallMoney

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