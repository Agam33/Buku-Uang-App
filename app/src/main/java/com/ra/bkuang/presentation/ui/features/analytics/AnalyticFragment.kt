package com.ra.bkuang.presentation.ui.features.analytics

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.ra.bkuang.R
import com.ra.bkuang.presentation.ui.base.BaseFragment
import com.ra.bkuang.databinding.FragmentAnalyticBinding
import com.ra.bkuang.presentation.viewmodel.AnalyticViewModel
import com.ra.bkuang.presentation.util.Constants.LOCALE_ID
import com.ra.bkuang.presentation.util.Constants.MONTHLY_DATE_FORMAT
import com.ra.bkuang.presentation.util.Extension.toStringFormat
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.presentation.ui.features.analytics.adapter.AnalyticListAdapter
import com.ra.bkuang.presentation.ui.features.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.features.transaction.TransactionType
import com.ra.bkuang.presentation.ui.features.transaction.getTransactionTypeID
import com.ra.bkuang.presentation.util.Extension.toMonthlyTime
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class AnalyticFragment : BaseFragment<FragmentAnalyticBinding>(R.layout.fragment_analytic) {

  private val viewModel: AnalyticViewModel by viewModels()

  private var transactionType: TransactionType = TransactionType.EXPENSE

  private var currentDate = LocalDate.now()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupDate()
    setupCategory()
    setupPieChart()
    selectedTransactionCategory()
  }

  private fun observer() {
    binding?.run {
      vm = viewModel
      lifecycleOwner = viewLifecycleOwner

      viewModel.analyticList.observe(viewLifecycleOwner) {
        when(it) {
          is Resource.Success -> {
            viewModel.setRvAnalyticState(false)

            val analyticAdapter = AnalyticListAdapter()
            analyticAdapter.submitList(it.data)

            rvTransaction.apply {
              adapter = analyticAdapter
              setHasFixedSize(true)
              layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

          }
          is Resource.Empty -> {
            viewModel.setRvAnalyticState(true)
          }
          is Resource.Loading -> {}
        }
      }

      viewModel.detailTransactions.observe(viewLifecycleOwner) {
        when(it) {
          is Resource.Loading -> {}
          is Resource.Empty -> {
            binding?.pieChart?.clear()
            binding?.pieChart?.invalidate()
          }
          is Resource.Success -> {
            setPieChartData(it.data ?: ArrayList())
          }
        }
      }
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
      valueTextSize = 12f
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