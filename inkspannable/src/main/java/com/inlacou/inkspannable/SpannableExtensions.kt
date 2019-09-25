package com.inlacou.inkspannable

import android.graphics.Typeface
import android.text.Annotation
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.util.Log
import android.view.View
import com.inlacou.inkspannable.round.RoundedBackgroundSpan
import kotlin.math.round

fun Spannable.applyMod(mod: TextSpanMod, from: Int = 0, to: Int = length): Spannable {
    Log.d("applyMod", "$from-$to: ${this.toString().substring(from, to)}")
    mod.typeface?.let { applyTypeface(it, from, to) }
    mod.onClick?.let { applyClickable(it, from, to) }
    mod.color?.let { applyColor(it, from, to) }
    mod.underline?.let { applyUnderline(it, from, to) }
    mod.round?.let { applyRounded(it, from, to) }
    mod.superScript?.let { applySuperScript(it, from, to) }
    mod.strike?.let { applyStrike(it, from, to) }
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

fun Spannable.applyAbsoluteSize(strike: Boolean, size: Int, dip: Boolean, from: Int = 0, to: Int = length): Spannable {
    if(strike) setSpan(AbsoluteSizeSpan(size, dip), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, SuperscriptSpan::class.java).forEach { removeSpan(it) }
    return this
}

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
