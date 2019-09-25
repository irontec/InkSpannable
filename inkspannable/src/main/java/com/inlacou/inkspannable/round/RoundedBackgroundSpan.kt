package com.inlacou.inkspannable.round

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import com.inlacou.inkspannable.InkSpannableConfig
import com.inlacou.inkspannable.R
import kotlin.math.roundToInt

@Deprecated("Use com.inlacou.inkspannable.roundRoundedBgTextView")
class RoundedBackgroundSpan : ReplacementSpan() {

	override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
		val margin = 7
		
		val left = x-margin
		val right = left + measureText(paint, text, start, end) + margin*2
		val rect = RectF(left, top.toFloat(), right, bottom.toFloat())
		
		//Almost working
		
		val drawable = InkSpannableConfig.context!!.get()!!.resources.getDrawable(R.drawable.rounded_text_bg)
		drawable.setBounds(left.toInt(),top,right.toInt(),bottom)
		drawable.draw(canvas)
		
		//paint.color = Color.BLUE
		//canvas.drawRoundRect(rect, 100f, 0f, paint)
		paint.color = Color.BLACK
		canvas.drawText(text, start, end, x, y.toFloat(), paint)
	}

	override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
		return measureText(paint, text, start, end).roundToInt()
	}

	private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
		return paint.measureText(text, start, end)
	}

}