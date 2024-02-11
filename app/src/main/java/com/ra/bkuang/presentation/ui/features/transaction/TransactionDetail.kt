package com.ra.bkuang.presentation.ui.features.transaction

import android.os.Parcelable
import java.time.LocalDateTime
import java.util.UUID

interface TransactionDetail: Parcelable {
   val uuid: UUID
   val transactionType: TransactionType
   val name1: String
   val name2: String
   val description: String
   val jumlah: Int
   val createdAt: LocalDateTime
   val updatedAt: LocalDateTime
}