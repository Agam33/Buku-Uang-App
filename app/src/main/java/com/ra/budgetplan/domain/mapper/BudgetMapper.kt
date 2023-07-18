package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.BudgetEntity
import com.ra.budgetplan.domain.model.BudgetModel

fun BudgetModel.toEntity(
): BudgetEntity = BudgetEntity(
  uuid,
  idKategori,
  deskripsi,
  pengeluaran,
  maxPengeluaran,
  bulanTahun,
  createdAt,
  updatedAt
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
