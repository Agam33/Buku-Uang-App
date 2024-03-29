package com.ra.bkuang.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ra.bkuang.data.local.database.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface HutangDao {

  @Insert
  suspend fun save(hutang: HutangEntity)

  @Delete
  suspend fun delete(hutang: HutangEntity)

  @Update
  suspend fun update(hutang: HutangEntity)

  @Query("SELECT * FROM hutang_tbl AS htg WHERE htg.uuid = :id")
  suspend fun findById(id: UUID): HutangEntity

  @Query("SELECT * FROM hutang_tbl AS htg WHERE htg.uuid = :id")
  fun findByIdWithFlow(id: UUID): Flow<HutangEntity>

  @Query("SELECT * FROM hutang_tbl ORDER BY updated_at DESC")
  suspend fun findAll(): List<HutangEntity>

  @Query("SELECT * FROM hutang_tbl AS htb WHERE htb.id_pengingat = :alarmId")
  suspend fun findByAlarmId(alarmId: Int): HutangEntity
}