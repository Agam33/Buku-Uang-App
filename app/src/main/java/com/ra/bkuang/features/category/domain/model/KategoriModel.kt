package com.ra.bkuang.features.category.domain.model

import android.os.Parcelable
import com.ra.bkuang.features.transaction.data.entity.TransactionType
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
