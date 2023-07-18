package com.ra.budgetplan.domain.entity

enum class TipeKategori {
  PENGELUARAN,
  PENDAPATAN,
  CICILAN
}

fun getCategoryName(name: String): TipeKategori =
  when(name.uppercase()) {
    TipeKategori.PENDAPATAN.name -> TipeKategori.PENDAPATAN
    TipeKategori.PENGELUARAN.name -> TipeKategori.PENGELUARAN
    else -> TipeKategori.CICILAN
  }