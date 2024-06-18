package com.ra.bkuang.features.analytics.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Constants.LOCALE_ID
import com.ra.bkuang.common.util.Constants.MONTHLY_DATE_FORMAT
import com.ra.bkuang.common.util.DrawerMenuToolbarListener
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.common.util.Extension.toMonthlyTime
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.databinding.FragmentAnalyticBinding
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.analytics.presentation.adapter.AnalyticListAdapter
import com.ra.bkuang.features.analytics.presentation.viewmodel.AnalyticViewModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
import com.ra.bkuang.features.transaction.presentation.TransactionType.Companion.getTransactionTypeID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AnalyticFragment : BaseFragment<FragmentAnalyticBinding>(R.layout.fragment_analytic) {

  private val viewModel: AnalyticViewModel by viewModels()

  @Inject lateinit var analyticAdapter: AnalyticListAdapter

  private var transactionType: TransactionType = TransactionType.EXPENSE

  var drawerMenuToolbarListener: DrawerMenuToolbarListener? = null

  private var currentDate = LocalDate.now()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupActionBar()
    setupDate()
    setupCategory()
    setupPieChart()
    selectedTransactionCategory()
  }

  private fun setupActionBar() {
    binding?.toolbar?.title = getString(R.string.txt_budget)

    binding?.toolbar?.setNavigationOnClickListener {
      drawerMenuToolbarListener?.onDrawerMenuClicked()
    }

    binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
      true
    }
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        if(uiState.analytics.isEmpty()) {
          binding?.rvTransaction?.hide(true)
        } else {
          binding?.rvTransaction?.hide(false)
          setupListTransaction(uiState.analytics)
        }

        if(uiState.detailAnalyticList.isEmpty()) {
          binding?.pieChart?.clear()
          binding?.pieChart?.invalidate()
        } else {
          setPieChartData(uiState.detailAnalyticList)
        }
      }
    }
  }

  private fun setupListTransaction(listModel: List<AnalyticModel>) {
    analyticAdapter.submitList(listModel)

    binding?.rvTransaction?.apply {
      adapter = analyticAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
  }

  private fun setupDate() {
    binding?.run {
      refresh()

      imgBtnNextDate.setOnClickListener {
        currentDate = currentDate.plusMonths(1)
        refresh()
      }

      imgBtnPrevDate.setOnClickListener {
        currentDate = currentDate.minusMonths(1)
        refresh()
      }
    }
  }

  private fun selectedTransactionCategory() {
    binding?.spCategory?.onItemSelectedListener = object : OnItemSelectedListener {
      override fun onItemSelected(adapter: AdapterView<*>?, v: View?, position: Int, p3: Long) {
        val type = adapter?.getItemAtPosition(position) as String
        transactionType = getTransactionTypeID(type)
        refresh()
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
  }

  private fun setupCategory() {
    binding?.run {
      val items = mutableListOf<String>()
      val types = requireContext().resources.getStringArray(R.array.transaction_type)

      for(i in 0 until types.size - 1) {
        items.add(types[i])
      }

      spCategory.adapter = ArrayAdapter(requireContext(), androidx.databinding.library.baseAdapters.R.layout.support_simple_spinner_dropdown_item, items)
    }
  }

  private fun setPieChartData(list :List<TransactionDetail>) {
    val transactionMap = transactionToMap(list)

    val entries = mutableListOf<PieEntry>()
    for(key in transactionMap.keys) {
      entries.add(
        PieEntry(
          transactionMap[key] ?: 0f,
          key
        )
      )
    }

    val dataset: PieDataSet = PieDataSet(entries, "").apply {
      setDrawIcons(false)
      sliceSpace = 3f
      valueTextSize = 8f
      iconsOffset = MPPointF(0F, 40F)
      selectionShift = 5f
      setDrawValues(false)
      colors = getListColor()
    }


    val pieData = PieData(dataset)
    pieData.setValueTextSize(11F)
    pieData.setValueTextColor(Color.WHITE)

    binding?.pieChart?.data = pieData

    // undo all highlights
    binding?.pieChart?.highlightValues(null)

    binding?.pieChart?.invalidate()
  }

  private fun getListColor(): List<Int> {
    // add a lot of colors
    val colors = mutableListOf<Int>()

    for(c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
    for(c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
    for(c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
    for(c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
    for(c in ColorTemplate.PASTEL_COLORS) colors.add(c)

    colors.add(ColorTemplate.getHoloBlue())

    return colors
  }

  private fun transactionToMap(list :List<TransactionDetail>): Map<String, Float> {
    val result: MutableMap<String, Float> = HashMap()
    for(item in list) {
      result[item.name1] = item.jumlah * 1f
    }
    return result
  }

  private fun setupPieChart() {
    binding?.run {

      pieChart.holeRadius = 36f
      pieChart.transparentCircleRadius = 48f

      // adding animation
      pieChart.animateY(1400, Easing.EaseInBack)

      val legend = pieChart.legend
      legend.apply {
        verticalAlignment = Legend.LegendVerticalAlignment.TOP
        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        orientation = Legend.LegendOrientation.VERTICAL
        setDrawInside(false)
        xEntrySpace = 7f
        yEntrySpace = 5f
        yOffset = 0f
        xOffset = 4f
      }

      pieChart.extraRightOffset = 40f

      // Entry label styling
      pieChart.setDrawEntryLabels(false)
      pieChart.setDrawCenterText(false)
    }
  }

  private fun refresh() {
    binding?.run {
      tvCurrentDate.text = currentDate.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
      val nowDate = currentDate.toMonthlyTime()
      viewModel.getAnalyticList(transactionType, nowDate.first, nowDate.second)
      viewModel.getDetailAnalytic(transactionType, nowDate.first, nowDate.second)
    }
  }
}