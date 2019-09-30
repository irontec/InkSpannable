package com.inlacou.inkspannable

import android.graphics.drawable.Drawable
import android.view.View

data class TextSpanMod(
		val typeface: TextStyles? = null,
		val underline: Boolean? = null,
		val round: Boolean? = null,
		val strike: Boolean? = null,
		val superScript: Boolean? = null,
		val quoteColor: Int? = null,
		val quoteStripeWidth: Int? = null,
		val quoteGapWidth: Int? = null,
		val bulletColor: Int? = null,
		val bulletGap: Int? = null,
		val bulletRadius: Int? = null,
		val color: Int? = null,
		val absoluteSize: Int? = null,
		val absoluteSizeDip: Boolean? = null,
		val relativeSize: Float? = null,
		val drawable: Drawable? = null,
		val drawableResId: Int? = null,
		val drawableWidth: Int? = null,
		val drawableHeight: Int? = null,
		val suggestions: List<String>? = null,
		val onClick: ((item: View?) -> Any)? = null
) {
	enum class TextStyles {
		NORMAL, BOLD, ITALIC, BOLD_ITALIC
	}
}