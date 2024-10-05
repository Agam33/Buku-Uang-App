package com.ra.bkuang.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ra.bkuang.core.data.source.local.database.entity.DetailPembayaranHutang
import com.ra.bkuang.core.data.source.local.database.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface PembayaranHutangDao {

  @Query("SELECT * FROM pembayaran_hutang_tbl AS pht " +
          "WHERE pht.id_hutang = :id " +
          "ORDER BY pht.updated_at DESC")
  fun findAllRecordByHutangId(id: UUID): Flow<List<DetailPembayaranHutang>>

  @Query("SELECT COUNT(*) FROM pembayaran_hutang_tbl AS pht WHERE pht.id_hutang = :id")
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>

  @Insert
  suspend fun save(pembayaran: PembayaranHutangEntity)

  @Delete
  suspend fun delete(pembayaran: PembayaranHutangEntity)

  @Update
  suspend fun update(pembayaran: PembayaranHutangEntity)
}