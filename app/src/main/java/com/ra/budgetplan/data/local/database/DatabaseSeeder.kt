package com.ra.budgetplan.data.local.database

import android.annotation.SuppressLint
import android.content.Context
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.dialog.icon.Icon
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.data.local.database.dao.IconDao
import com.ra.budgetplan.data.local.database.dao.KategoriDao
import com.ra.budgetplan.domain.entity.IconEntity
import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.entity.TipeKategori.*
import com.ra.budgetplan.util.coroutineIOThread
import java.time.LocalDateTime
import java.util.UUID

class DatabaseSeeder(
  private val context: Context,
  private val kategoriDao: KategoriDao,
  private val iconDao: IconDao
) {

  @SuppressLint("DiscouragedApi")
  fun seedDataCategory() {
    val categories = mutableListOf<KategoriEntity>()
    val resource = context.resources

    // Expense
    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense__cake_36),
        "drawable", context.packageName),
      nama = "Ulang Tahun",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_build_36), "drawable", context.packageName
      ),
      nama = "Perbaikan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =  resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_child_friendly_24), "drawable", context.packageName
      ),
      nama = "Keperluan Bayi",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_coffee_36), "drawable", context.packageName
      ),
      nama = "Kopi",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_electric_bolt_36), "drawable", context.packageName
      ),
      nama = "Bayar Listrik",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =  resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_food_36), "drawable", context.packageName
      ),
      nama = "Makanan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_local_hospital_36), "drawable", context.packageName
      ),
      nama = "Kesehatan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =  resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_school_36), "drawable", context.packageName
      ),
      nama = "Pendidikan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_wifi_36), "drawable", context.packageName
      ),
      nama = "Wifi",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.expense_shopping_bag_36), "drawable", context.packageName
      ),
      nama = "Belanja",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    // Income
    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_corporate_fare_36), "drawable", context.packageName
      ),
      nama = "Pekerjaan",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_games_36), "drawable", context.packageName
      ),
      nama = "Bermain Game",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_grass_36), "drawable", context.packageName
      ),
      nama = "Bertanam",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_handshake_36), "drawable", context.packageName
      ),
      nama = "Bisnis",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_live_tv_36), "drawable", context.packageName
      ),
      nama = "Youtube",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_pedal_bike_36), "drawable", context.packageName
      ),
      nama = "Ngojek",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_trending_up_36), "drawable", context.packageName
      ),
      nama = "Trading",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_workspace_premium_36), "drawable", context.packageName
      ),
      nama = "Hadiah Juara",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_photo_camera_front_36), "drawable", context.packageName
      ),
      nama = "Rekaman",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    categories.add(KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =resource.getIdentifier(
        resource.getResourceEntryName(R.drawable.income_diamond_36), "drawable", context.packageName
      ),
      nama = "Jualan",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ))

    coroutineIOThread {
      kategoriDao.saveAll(categories)
    }
  }
  
  fun seedDataIcon() {
    // all icon
    val icons = setIncomeIcon(context) + setExpensesIcon(context) + setIconAccount(context)
    coroutineIOThread {
      iconDao.addAll(icons)
    }
  }

  @SuppressLint("DiscouragedApi")
  private fun setIconAccount(context: Context): MutableList<IconEntity> {
    val resource = context.resources
    return mutableListOf(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.ACCOUNT,
        resource.getIdentifier(
          resource.getResourceEntryName(
            R.drawable.account_credit_card_36),
          "drawable", context.packageName)
      ),
      IconEntity(
        UUID.randomUUID(),
        IconCategory.ACCOUNT,
        resource.getIdentifier(
          resource.getResourceEntryName(
            R.drawable.account_cash_round_money_36),
          "drawable", context.packageName)
      ),
      IconEntity(
        UUID.randomUUID(),
        IconCategory.ACCOUNT,
        resource.getIdentifier(
          resource.getResourceEntryName(
            R.drawable.account_savings_36),
          "drawable", context.packageName)
      )
    )
  }

  @SuppressLint("DiscouragedApi")
  private fun setExpensesIcon(context: Context): MutableList<IconEntity> {
    val expenseIcon = mutableListOf<IconEntity>()
    val resource = context.resources
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense__cake_36),
          "drawable", context.packageName)
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_build_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_car_repair_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_child_friendly_24), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_coffee_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_dry_cleaning_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_electric_bolt_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_electric_moped_36), "drawable", context.packageName
        )
      )
    )

    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_family_restroom_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_flight_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_food_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_headset_mic_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_hiking_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_how_to_vote_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_icecream_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_kayaking_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_keyboard_24), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_laptop_mac_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_liquor_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_local_hospital_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_note_alt_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_phone_android_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_school_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_shopping_bag_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_shopping_cart_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_shower_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_sports_soccer_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_wifi_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      IconEntity(

        UUID.randomUUID(),
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_wine_bar_36), "drawable", context.packageName
        )
      )
    )
    return expenseIcon
  }

  @SuppressLint("DiscouragedApi")
  private fun setIncomeIcon(context: Context): MutableList<IconEntity> {
    val incomeIcon = mutableListOf<IconEntity>()
    val resource = context.resources
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_attach_money_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_car_rental_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_cloud_upload_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_computer_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_corporate_fare_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_diamond_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_games_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_grass_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_handshake_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_hardware_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_live_tv_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_pedal_bike_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_photo_camera_front_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_storage_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_trending_up_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      IconEntity(
        UUID.randomUUID(),
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_workspace_premium_36), "drawable", context.packageName
        )
      )
    )
    return incomeIcon
  }
}