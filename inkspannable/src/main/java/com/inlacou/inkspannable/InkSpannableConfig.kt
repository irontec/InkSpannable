package com.inlacou.inkspannable

import android.content.Context

object InkSpannableConfig {
	
	/**
	 * Pass Application context here
	 */
	var context: Context? = null
	val instance get() = context!!
	
}