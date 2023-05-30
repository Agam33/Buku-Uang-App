package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.BudgetEntity
import com.ra.budgetplan.domain.model.BudgetModel

fun budgetToEntity(
  budgetModel: BudgetModel
): BudgetEntity = BudgetEntity(
  budgetModel.uuid,
  budgetModel.idKategori,
  budgetModel.deskripsi,
  budgetModel.pengeluaran,
  budgetModel.maxPengeluaran,
  budgetModel.bulanTahun,
  budgetModel.createdAt,
  budgetModel.updatedAt
)


fun BudgetEntity.toModel(): BudgetModel = BudgetModel(
  uuid,
  idKategori,
  deskripsi,
  pengeluaran,
  maxPengeluaran,
  bulanTahun,
  createdAt,
  updatedAt
)
