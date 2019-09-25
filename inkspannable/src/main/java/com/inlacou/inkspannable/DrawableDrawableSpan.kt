package com.inlacou.inkspannable

import android.graphics.drawable.Drawable
import android.text.style.DynamicDrawableSpan

class DrawableDrawableSpan(
		private val item: Drawable,
		private val width: Int? = null,
		private val height: Int? = null,
		val alignment: Int = ALIGN_BASELINE
): DynamicDrawableSpan() {
	
	override fun getVerticalAlignment(): Int {
		return alignment
	}
	
	override fun getDrawable(): Drawable {
		item.setBounds(0, 0, width ?: item.intrinsicWidth, height ?: item.intrinsicHeight)
		return item
	}
	
}