package com.inlacou.inkspannable

import android.view.View

data class TextSpanMod(
		val typeface: TextStyles? = null,
		val underline: Boolean? = null,
		val round: Boolean? = null,
		val color: Int? = null,
		val onClick: ((item: View?) -> Any)? = null
) {
	enum class TextStyles {
		NORMAL, BOLD, ITALIC, BOLD_ITALIC
	}
}