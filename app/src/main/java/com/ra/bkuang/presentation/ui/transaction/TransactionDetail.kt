package com.ra.bkuang.presentation.ui.transaction

import android.os.Parcelable
import java.time.LocalDateTime
import java.util.UUID

interface TransactionDetail: Parcelable {
   val uuid: UUID
   val transactionType: TransactionType
   val name1: String
   val name2: String
   val icUrl1: Int
   val icUrl2: Int
   val description: String
   val jumlah: Int
   val createdAt: LocalDateTime
   val updatedAt: LocalDateTime
}