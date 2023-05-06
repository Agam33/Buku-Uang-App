package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.BudgetEntity
import com.ra.budgetplan.domain.model.BudgetModel

fun budgetToModel(
  budgetEntity: BudgetEntity
): BudgetModel = BudgetModel(
  budgetEntity.uuid,
  budgetEntity.idKategori,
  budgetEntity.deskripsi,
  budgetEntity.pengeluaran,
  budgetEntity.maxPengeluaran,
  budgetEntity.createdAt,
  budgetEntity.updatedAt
)

fun budgetToEntity(
  budgetModel: BudgetModel
): BudgetModel = BudgetModel(
  budgetModel.uuid,
  budgetModel.idKategori,
  budgetModel.deskripsi,
  budgetModel.pengeluaran,
  budgetModel.maxPengeluaran,
  budgetModel.createdAt,
  budgetModel.updatedAt
)