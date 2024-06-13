package com.ra.bkuang.common.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

class ZoomOutPageTransformer: ViewPager2.PageTransformer {
  companion object {
    private const val MIN_SCALE = 0.85f
  }

  override fun transformPage(page: View, position: Float) {
    page.apply {

      val pageWidth = width
      val pageHeight = height

      when {
        position < -1 -> { // [-Infinity,-1)
          alpha = 1f
        }

        position <= 1 -> { // [-1, 1]
          // Modify the default slide transition to shrink the page as well.
          val scaleFactor = max(MIN_SCALE, 1 - abs(position))
          val vertMargin = pageHeight * (1 - scaleFactor) / 2
          val horzMargin = pageWidth * (1 - scaleFactor) / 2

          translationX = if(position < 0) {
            horzMargin - vertMargin / 2
          } else {
            horzMargin + vertMargin / 2
          }

          scaleX = scaleFactor
          scaleY = scaleFactor
        }

        else -> { // (1,+Infinity]
          alpha = 1f
        }
      }
    }
  }
}