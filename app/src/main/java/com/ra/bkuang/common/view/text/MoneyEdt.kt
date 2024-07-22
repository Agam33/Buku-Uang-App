package com.ra.bkuang.common.view.text

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.ra.bkuang.R
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale
class MoneyEdt: TextInputEditText {

  val maxInput = 2_000_000_000

  val isMaximumInput: Boolean
    get() = cleanString(text.toString()).toLong() > maxInput

  private val mEdt = this
  private val numFormat = NumberFormat.getInstance(Locale("id", "ID"))

  constructor(context: Context) : super(context) { init() }
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { init() }
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    init()
  }

  private fun init() {
    inputType = android.text.InputType.TYPE_CLASS_NUMBER
    setLines(1)
    filters = arrayOf(InputFilter.LengthFilter(13))

    mEdt.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(!s.isNullOrBlank() || s.toString().isNotEmpty()) {

          mEdt.removeTextChangedListener(this)

          val cleanString = cleanString(s.toString())

          try {
            val formatted = formatString(cleanString)

            if(isMaximumInput) {
              setTextColor(context.getColor(R.color.red_40))
            } else {
              setTextColor(context.getColor(R.color.black))
            }

            setText(formatted)
            setSelection(formatted.length)
          } catch (e: Exception) {
            e.printStackTrace()
          }

          mEdt.addTextChangedListener(this)
        }
      }

      override fun afterTextChanged(s: Editable?) {}

    })
  }

  private fun cleanString(text: String): String {
    // https://stackoverflow.com/a/10372905/1595442
    return text.replace("[^\\d]".toRegex(), "")
  }

  private fun formatString(text: String): String {
    return numFormat.format(
      if(text.isEmpty() || text.isBlank()) 0 else text.toDouble()
    )
  }

  @Throws(MaxInputException::class)
  fun getIntValue(): Int {
    if(!isMaximumInput) return if(text.toString().isBlank()) 0 else cleanString(text.toString()).toInt()
    throw MaxInputException()
  }

  inner class MaxInputException: IOException("Max input is ${numFormat.format(maxInput)}")
}