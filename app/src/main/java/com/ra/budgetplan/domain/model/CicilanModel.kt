package com.ra.budgetplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class CicilanModel(
 var uuid: UUID,
 var deskripsi: String,
 var totalPengeluaran: Int,
 var maxCicilan: Int,
 var idPengingat: Int,
 var jatuhTempo: LocalDate,
 var createdAt: LocalDateTime,
 var updatedAt: LocalDateTime
) : Parcelable