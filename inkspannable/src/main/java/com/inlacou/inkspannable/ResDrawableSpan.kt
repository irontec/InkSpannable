package com.inlacou.inkspannable

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.style.DynamicDrawableSpan

class ResDrawableSpan(
		private val resId: Int,
		private val width: Int? = null,
		private val height: Int? = null,
		private val alignment: Int = ALIGN_BASELINE
): DynamicDrawableSpan() {
	
	private fun Resources.getDrawableCompat(resId: Int): Drawable {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			this.getDrawable(resId, null)
		}else{
			this.getDrawable(resId)
		}
	}
	
	override fun getVerticalAlignment(): Int {
		return alignment
	}
	
	private fun Context.getDrawableCompat(resId: Int): Drawable {
		return resources.getDrawableCompat(resId)
	}
	
	override fun getDrawable(): Drawable {
		val aux = 0
		val drawable = InkSpannableConfig.context!!.get()!!.getDrawableCompat(resId)
		drawable.setBounds(0, aux, width ?: drawable.intrinsicWidth, aux+(height ?: drawable.intrinsicHeight))
		return drawable
	}
	
}