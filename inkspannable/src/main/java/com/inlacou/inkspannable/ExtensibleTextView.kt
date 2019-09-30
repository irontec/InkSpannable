package com.inlacou.inkspannable

import android.content.Context
import android.graphics.Canvas
import android.text.Spanned
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

open class ExtensibleTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {
	
	val drawInterceptors: MutableList<(Canvas) -> Unit> = mutableListOf()
	
	override fun onDraw(canvas: Canvas) {
		if(text is Spanned && layout != null){
			drawInterceptors.forEach { it.invoke(canvas) }
		}
		super.onDraw(canvas)
	}
}