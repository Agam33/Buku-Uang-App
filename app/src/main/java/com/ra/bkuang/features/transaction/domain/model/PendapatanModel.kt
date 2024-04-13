package com.ra.bkuang.features.transaction.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class PendapatanModel(
 var uuid: UUID,
 var idKategori: UUID,
 var idAkun: UUID,
 var deskripsi: String,
 var jumlah: Int,
 var createdAt: LocalDateTime,
 var updatedAt: LocalDateTime
) : Parcelable
