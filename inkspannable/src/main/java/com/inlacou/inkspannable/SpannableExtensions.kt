package com.inlacou.inkspannable

import android.graphics.Typeface
import android.text.Annotation
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.util.Log
import android.view.View

fun Spannable.applyMod(modifier: TextSpanMod, from: Int = 0, to: Int = length): Spannable {
    modifier.typeface?.let { applyTypeface(it, from, to) }
    modifier.onClick?.let { applyClickable(it, from, to) }
    modifier.color?.let { applyColor(it, from, to) }
    modifier.underline?.let { applyUnderline(it, from, to) }
    modifier.round?.let { applyRounded(it, from, to) }
    modifier.superScript?.let { applySuperScript(it, from, to) }
    modifier.relativeSize?.let { applyRelativeSize(it, from, to) }
    modifier.absoluteSize?.let { applyAbsoluteSize(it, modifier.absoluteSizeDip ?: true, from, to) }
    modifier.strike?.let { applyStrike(it, from, to) }
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

fun Spannable.applyStrike(strike: Boolean, from: Int = 0, to: Int = length): Spannable {
    if(strike) setSpan(StrikethroughSpan(), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, StrikethroughSpan::class.java).forEach { removeSpan(it) }
    return this
}

fun Spannable.applySuperScript(strike: Boolean, from: Int = 0, to: Int = length): Spannable {
    if(strike) setSpan(SuperscriptSpan(), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, SuperscriptSpan::class.java).forEach { removeSpan(it) }
    return this
}

fun Spannable.applyAbsoluteSize(size: Int, dip: Boolean = true, from: Int = 0, to: Int = length): Spannable {
    setSpan(AbsoluteSizeSpan(size, dip), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * @param size 1.5f would make the size increase to 150%, 0.5f would make it 50%.
 */
fun Spannable.applyRelativeSize(size: Float, from: Int = 0, to: Int = length): Spannable {
    setSpan(RelativeSizeSpan(size), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyRounded(rounded: Boolean, from: Int = 0, to: Int = length): Spannable {
    if(rounded) setSpan(Annotation("rounded", "rounded"), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, Annotation::class.java).filter { it.key == "rounded" }.forEach { removeSpan(it) }
    return this
}

fun Spannable.applyColor(color: Int, from: Int = 0, to: Int = length): Spannable {
    setSpan(ForegroundColorSpan(color), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyClickable(onClick: ((item: View?) -> Any)?, from: Int = 0, to: Int = length): Spannable {
    setSpan(object: ClickableSpan() {
        override fun onClick(widget: View) { onClick?.invoke(widget) }
    }, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}
