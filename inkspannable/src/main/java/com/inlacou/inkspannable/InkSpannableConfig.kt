package com.inlacou.inkspannable

import android.content.Context
import java.lang.ref.WeakReference

object InkSpannableConfig {
	
	/**
	 * Pass Application context here
	 */
	var context: WeakReference<Context>? = null
	val instance get() = context?.get()!!
	
}