package com.ra.bkuang.features.budget.di

import com.ra.bkuang.features.budget.domain.usecase.CreateBudget
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetById
import com.ra.bkuang.features.budget.domain.usecase.EditBudget
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDate
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetById
import com.ra.bkuang.features.budget.domain.usecase.impl.CreateBudgetImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.DeleteBudgetByIdImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.EditBudgetImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.FindAllBudgetByDateImpl
import com.ra.bkuang.features.budget.domain.usecase.impl.FindBudgetByIdImpl
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
  fun bindCreateBudget(createBudgetImpl: CreateBudgetImpl): CreateBudget

  @Binds
  @Singleton
  fun bindFindBudgetById(findBudgetByIdImpl: FindBudgetByIdImpl): FindBudgetById

  @Binds
  @Singleton
  fun bindEditBudget(editBudgetImpl: EditBudgetImpl): EditBudget

  @Binds
  @Singleton
  fun bindDeleteBudget(deleteBudgetByIdImpl: DeleteBudgetByIdImpl): DeleteBudgetById

  @Binds
  @Singleton
  fun bindFindAllBudgetByDate(findAllBudgetByDateImpl: FindAllBudgetByDateImpl): FindAllBudgetByDate
}