package com.ra.bkuang.domain.model

import android.os.Parcelable
import com.ra.bkuang.presentation.ui.transaction.TransactionType
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