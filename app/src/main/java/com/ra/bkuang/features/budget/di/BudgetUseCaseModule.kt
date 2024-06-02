package com.ra.bkuang.features.budget.di

import com.ra.bkuang.features.budget.domain.usecase.CreateBudgetUseCase
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetByIdUseCase
import com.ra.bkuang.features.budget.domain.usecase.EditBudgetUseCase
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDateUseCase
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetByIdUseCase
import com.ra.bkuang.features.budget.domain.usecase.impl.CreateBudgetUseCaseImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.DeleteBudgetByIdUseCaseImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.EditBudgetUseCaseImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.FindAllBudgetByDateUseCaseImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.FindBudgetByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BudgetUseCaseModule {

  @Binds
  @Singleton
  fun bindCreateBudget(createBudgetImpl: CreateBudgetUseCaseImpl): CreateBudgetUseCase

  @Binds
  @Singleton
  fun bindFindBudgetById(findBudgetByIdImpl: FindBudgetByIdUseCaseImpl): FindBudgetByIdUseCase

  @Binds
  @Singleton
  fun bindEditBudget(editBudgetImpl: EditBudgetUseCaseImpl): EditBudgetUseCase

  @Binds
  @Singleton
  fun bindDeleteBudget(deleteBudgetByIdImpl: DeleteBudgetByIdUseCaseImpl): DeleteBudgetByIdUseCase

  @Binds
  @Singleton
  fun bindFindAllBudgetByDate(findAllBudgetByDateImpl: FindAllBudgetByDateUseCaseImpl): FindAllBudgetByDateUseCase
}