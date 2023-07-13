package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.budget.CreateBudget
import com.ra.budgetplan.domain.usecase.budget.DeleteBudgetById
import com.ra.budgetplan.domain.usecase.budget.EditBudget
import com.ra.budgetplan.domain.usecase.budget.FindAllBudgetByDate
import com.ra.budgetplan.domain.usecase.budget.FindBudgetById
import com.ra.budgetplan.domain.usecase.budget.impl.CreateBudgetImpl
import com.ra.budgetplan.domain.usecase.budget.impl.DeleteBudgetByIdImpl
import com.ra.budgetplan.domain.usecase.budget.impl.EditBudgetImpl
import com.ra.budgetplan.domain.usecase.budget.impl.FindAllBudgetByDateImpl
import com.ra.budgetplan.domain.usecase.budget.impl.FindBudgetByIdImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseBudgetModule {

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