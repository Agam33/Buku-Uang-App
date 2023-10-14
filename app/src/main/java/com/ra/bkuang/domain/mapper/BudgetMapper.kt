package com.ra.bkuang.domain.mapper

import com.ra.bkuang.domain.entity.BudgetEntity
import com.ra.bkuang.domain.model.BudgetModel

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
