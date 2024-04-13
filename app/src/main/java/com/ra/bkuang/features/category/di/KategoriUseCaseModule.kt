package com.ra.bkuang.features.category.di

import com.ra.bkuang.features.account.domain.usecase.FindCategoryByType
import com.ra.bkuang.features.account.domain.usecase.impl.FindCategoryByTypeImpl
import com.ra.bkuang.features.category.domain.usecase.DeleteKategori
import com.ra.bkuang.features.category.domain.usecase.FindAllKategori
import com.ra.bkuang.features.category.domain.usecase.FindKategoriById
import com.ra.bkuang.features.category.domain.usecase.SaveKategori
import com.ra.bkuang.features.category.domain.usecase.UpdateKategori
import com.ra.bkuang.features.category.domain.usecase.impl.DeleteKategoriImpl
import com.ra.bkuang.features.category.domain.usecase.impl.FindAllKategoriImpl
import com.ra.bkuang.features.category.domain.usecase.impl.FindKategoriByIdImpl
import com.ra.bkuang.features.category.domain.usecase.impl.SaveKategoriImpl
import com.ra.bkuang.features.category.domain.usecase.impl.UpdateKategoriImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface KategoriUseCaseModule {

  @Binds
  @Singleton
  fun bindFindCategoryByType(findCategoryByTypeImpl: FindCategoryByTypeImpl): FindCategoryByType

  @Binds
  @Singleton
  fun bindSaveKategoriUseCase(saveKategoriImpl: SaveKategoriImpl): SaveKategori

  @Binds
  @Singleton
  fun bindDeleteKategoriUseCase(deleteKategoriImpl: DeleteKategoriImpl): DeleteKategori

  @Binds
  @Singleton
  fun bindUpdateKategoriUseCase(updateKategoriImpl: UpdateKategoriImpl): UpdateKategori

  @Binds
  @Singleton
  fun bindFindAllKategoriUseCase(findAllKategoriImpl: FindAllKategoriImpl): FindAllKategori

  @Binds
  @Singleton
  fun bindFindKategoriByIdUseCase(findKategoriByIdImpl: FindKategoriByIdImpl): FindKategoriById
}