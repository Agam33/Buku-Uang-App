package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.BudgetEntity

@Dao
interface BudgetDao {

  @Insert
  fun save(budget: BudgetEntity)

  @Delete
  fun delete(budget: BudgetEntity)

  @Update
  fun update(budget: BudgetEntity)
}