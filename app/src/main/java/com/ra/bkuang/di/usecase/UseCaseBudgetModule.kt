package com.ra.bkuang.di.usecase

import com.ra.bkuang.domain.usecase.budget.CreateBudget
import com.ra.bkuang.domain.usecase.budget.DeleteBudgetById
import com.ra.bkuang.domain.usecase.budget.EditBudget
import com.ra.bkuang.domain.usecase.budget.FindAllBudgetByDate
import com.ra.bkuang.domain.usecase.budget.FindBudgetById
import com.ra.bkuang.domain.usecase.budget.impl.CreateBudgetImpl
import com.ra.bkuang.domain.usecase.budget.impl.DeleteBudgetByIdImpl
import com.ra.bkuang.domain.usecase.budget.impl.EditBudgetImpl
import com.ra.bkuang.domain.usecase.budget.impl.FindAllBudgetByDateImpl
import com.ra.bkuang.domain.usecase.budget.impl.FindBudgetByIdImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseBudgetModule {

  @Binds
  fun bindCreateBudget(createBudgetImpl: CreateBudgetImpl): CreateBudget

  @Binds
  fun bindFindBudgetById(findBudgetByIdImpl: FindBudgetByIdImpl): FindBudgetById

  @Binds
  fun bindEditBudget(editBudgetImpl: EditBudgetImpl): EditBudget

  @Binds
  fun bindDeleteBudget(deleteBudgetByIdImpl: DeleteBudgetByIdImpl): DeleteBudgetById

  @Binds
  fun bindFindAllBudgetByDate(findAllBudgetByDateImpl: FindAllBudgetByDateImpl): FindAllBudgetByDate
}