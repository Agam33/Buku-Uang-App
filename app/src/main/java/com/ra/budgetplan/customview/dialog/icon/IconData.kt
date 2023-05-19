package com.ra.budgetplan.customview.dialog.icon

import android.annotation.SuppressLint
import android.content.Context
import com.ra.budgetplan.R

object IconData {

  @SuppressLint("DiscouragedApi")
  fun setExpensesIcon(context: Context): MutableList<Icon> {
    val expenseIcon = mutableListOf<Icon>()
    val resource = context.resources
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(
            R.drawable.expense__cake_36),
          "drawable", context.packageName)
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_build_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_car_repair_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_child_friendly_24), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_coffee_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_dry_cleaning_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_electric_bolt_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_electric_moped_36), "drawable", context.packageName
        )
      )
    )

    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_family_restroom_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_flight_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_food_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_headset_mic_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_hiking_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_how_to_vote_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_icecream_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_kayaking_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_keyboard_24), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_laptop_mac_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_liquor_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_local_hospital_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_note_alt_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_phone_android_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_school_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_shopping_bag_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_shopping_cart_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_shower_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_sports_soccer_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_wifi_36), "drawable", context.packageName
        )
      )
    )
    expenseIcon.add(
      Icon(
        IconCategory.EXPENSE,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.expense_wine_bar_36), "drawable", context.packageName
        )
      )
    )
    return expenseIcon
  }

  @SuppressLint("DiscouragedApi")
  fun setIncomeIcon(context: Context): MutableList<Icon> {
    val incomeIcon = mutableListOf<Icon>()
    val resource = context.resources
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_attach_money_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_car_rental_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_cloud_upload_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_computer_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_corporate_fare_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_diamond_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_games_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_grass_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_handshake_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_hardware_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_live_tv_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_pedal_bike_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_photo_camera_front_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_storage_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_trending_up_36), "drawable", context.packageName
        )
      )
    )
    incomeIcon.add(
      Icon(
        IconCategory.INCOME,
        resource.getIdentifier(
          resource.getResourceEntryName(R.drawable.income_workspace_premium_36), "drawable", context.packageName
        )
      )
    )
    return incomeIcon
  }
}