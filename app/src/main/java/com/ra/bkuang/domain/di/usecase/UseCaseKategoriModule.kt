package com.ra.bkuang.domain.di.usecase

import com.ra.bkuang.domain.usecase.akun.FindCategoryByType
import com.ra.bkuang.domain.usecase.akun.impl.FindCategoryByTypeImpl
import com.ra.bkuang.domain.usecase.kategori.DeleteKategori
import com.ra.bkuang.domain.usecase.kategori.FindAllKategori
import com.ra.bkuang.domain.usecase.kategori.FindKategoriById
import com.ra.bkuang.domain.usecase.kategori.SaveKategori
import com.ra.bkuang.domain.usecase.kategori.UpdateKategori
import com.ra.bkuang.domain.usecase.kategori.impl.DeleteKategoriImpl
import com.ra.bkuang.domain.usecase.kategori.impl.FindAllKategoriImpl
import com.ra.bkuang.domain.usecase.kategori.impl.FindKategoriByIdImpl
import com.ra.bkuang.domain.usecase.kategori.impl.SaveKategoriImpl
import com.ra.bkuang.domain.usecase.kategori.impl.UpdateKategoriImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseKategoriModule {

  @Binds
  fun bindFindCategoryByType(findCategoryByTypeImpl: FindCategoryByTypeImpl): FindCategoryByType

  @Binds
  fun bindSaveKategoriUseCase(saveKategoriImpl: SaveKategoriImpl): SaveKategori

  @Binds
  fun bindDeleteKategoriUseCase(deleteKategoriImpl: DeleteKategoriImpl): DeleteKategori

  @Binds
  fun bindUpdateKategoriUseCase(updateKategoriImpl: UpdateKategoriImpl): UpdateKategori

  @Binds
  fun bindFindAllKategoriUseCase(findAllKategoriImpl: FindAllKategoriImpl): FindAllKategori

  @Binds
  fun bindFindKategoriByIdUseCase(findKategoriByIdImpl: FindKategoriByIdImpl): FindKategoriById
}