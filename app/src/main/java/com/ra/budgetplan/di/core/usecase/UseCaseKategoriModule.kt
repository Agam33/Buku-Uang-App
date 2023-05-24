package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.akun.FindCategoryByType
import com.ra.budgetplan.domain.usecase.akun.impl.FindCategoryByTypeImpl
import com.ra.budgetplan.domain.usecase.kategori.DeleteKategori
import com.ra.budgetplan.domain.usecase.kategori.FindAllKategori
import com.ra.budgetplan.domain.usecase.kategori.FindKategoriById
import com.ra.budgetplan.domain.usecase.kategori.SaveKategori
import com.ra.budgetplan.domain.usecase.kategori.UpdateKategori
import com.ra.budgetplan.domain.usecase.kategori.impl.DeleteKategoriImpl
import com.ra.budgetplan.domain.usecase.kategori.impl.FindAllKategoriImpl
import com.ra.budgetplan.domain.usecase.kategori.impl.FindKategoriByIdImpl
import com.ra.budgetplan.domain.usecase.kategori.impl.SaveKategoriImpl
import com.ra.budgetplan.domain.usecase.kategori.impl.UpdateKategoriImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseKategoriModule {

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