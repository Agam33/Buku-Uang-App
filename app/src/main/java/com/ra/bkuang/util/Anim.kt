package com.ra.bkuang.util

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

object Anim {
  fun expandedWidth(v: View, duration: Long, targetWidth: Int) {
    val prevWidth = v.width

    v.visibility = View.VISIBLE
    val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
    valueAnimator.addUpdateListener { animation ->
      v.layoutParams.width = animation.animatedValue as Int
      v.requestLayout()
    }

    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
  }

  fun collapsedWidth(v: View, duration: Long, targetWidth: Int) {
    val prevWidth = v.width
    val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
    valueAnimator.addUpdateListener { animation ->
      v.layoutParams.width = animation.animatedValue as Int
      v.requestLayout()
    }

    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
  }
}