package com.ra.budgetplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class HutangModel(
 var uuid: UUID,
 var nama: String,
 var deskripsi: String,
 var totalPengeluaran: Int,
 var maxCicilan: Int,
 var idPengingat: Int,
 var pengingatAktif: Boolean,
 val tglPengingat: String,
 var jatuhTempo: LocalDateTime,
 var createdAt: LocalDateTime,
 var updatedAt: LocalDateTime
) : Parcelable
