package com.inlacou.inkspannable

import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.view.View

fun Spannable.applyMod(mod: TextSpanMod, from: Int = 0, to: Int = length): Spannable {
    mod.typeface?.let { applyTypeface(it, from, to) }
    mod.onClick?.let { applyClickable(it, from, to) }
    mod.color?.let { applyColor(it, from, to) }
    mod.underline?.let { applyUnderline(it, from, to) }
    return this
}

fun Spannable.applyTypeface(typeface: TextSpanMod.TextStyles, from: Int = 0, to: Int = length): Spannable {
    setSpan(when(typeface){
        TextSpanMod.TextStyles.NORMAL -> StyleSpan(Typeface.NORMAL)
        TextSpanMod.TextStyles.BOLD_ITALIC -> StyleSpan(Typeface.BOLD_ITALIC)
        TextSpanMod.TextStyles.ITALIC -> StyleSpan(Typeface.ITALIC)
        TextSpanMod.TextStyles.BOLD -> StyleSpan(Typeface.BOLD)
    }, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyUnderline(underline: Boolean, from: Int = 0, to: Int = length): Spannable {
    setSpan(object : UnderlineSpan(){
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = underline
        }
    }, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyColor(color: Int, from: Int = 0, to: Int = length): Spannable {
    setSpan(ForegroundColorSpan(color), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applySize(size: Float, from: Int = 0, to: Int = length): Spannable {
    setSpan(RelativeSizeSpan(size), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyClickable(onClick: ((item: View?) -> Any)?, from: Int = 0, to: Int = length): Spannable {
    setSpan(object: ClickableSpan() {
        override fun onClick(widget: View) { onClick?.invoke(widget) }
    }, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}
