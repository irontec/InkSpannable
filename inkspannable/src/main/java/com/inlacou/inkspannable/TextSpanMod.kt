package com.inlacou.inkspannable

import android.view.View

data class TextSpanMod(
		val typeface: TextStyles? = null,
		val underline: Boolean? = null,
		val round: Boolean? = null,
		val strike: Boolean? = null,
		val superScript: Boolean? = null,
		val color: Int? = null,
		val absoluteSize: Int? = null,
		val absoluteSizeDip: Boolean? = null,
		val relativeSize: Float? = null,
		val onClick: ((item: View?) -> Any)? = null
) {
	enum class TextStyles {
		NORMAL, BOLD, ITALIC, BOLD_ITALIC
	}
}