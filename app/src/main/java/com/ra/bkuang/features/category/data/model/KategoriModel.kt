package com.ra.bkuang.features.category.data.model

import android.os.Parcelable
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class KategoriModel(
    var uuid: UUID,
    var nama: String,
    var tipeKategori: TransactionType,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) : Parcelable
