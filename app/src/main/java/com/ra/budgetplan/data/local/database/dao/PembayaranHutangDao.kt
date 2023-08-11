package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ra.budgetplan.domain.entity.DetailPembayaranHutang
import com.ra.budgetplan.domain.entity.PembayaranHutangEntity
import java.util.UUID

@Dao
interface PembayaranHutangDao {

  @Query("SELECT * FROM pembayaran_hutang_tbl AS pht " +
          "WHERE pht.id_hutang = :id " +
          "ORDER BY pht.updated_at DESC")
  suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang>

  @Insert
  suspend fun save(pembayaran: PembayaranHutangEntity)

  @Delete
  suspend fun delete(pembayaran: PembayaranHutangEntity)

  @Update
  suspend fun update(pembayaran: PembayaranHutangEntity)
}