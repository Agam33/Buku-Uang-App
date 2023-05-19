package com.ra.budgetplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class BudgetModel(
  var uuid: UUID,
  var idKategori: UUID,
  var deskripsi: String,
  var pengeluaran: Int,
  var maxPengeluaran: Int,
  var bulanTahun: LocalDate,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
