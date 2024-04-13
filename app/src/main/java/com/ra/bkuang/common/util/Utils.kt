package com.ra.bkuang.common.util

object Utils {

  fun calculatePercent(currValue: Int, maxValue: Long): Double =
        ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0

}