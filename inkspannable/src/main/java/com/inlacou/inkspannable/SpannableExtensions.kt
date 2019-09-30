package com.inlacou.inkspannable

import android.content.res.Resources
import android.graphics.BlurMaskFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Annotation
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.inlacou.inkspannable.spans.DrawableDrawableSpan
import com.inlacou.inkspannable.spans.ResDrawableSpan

fun Spannable.applyMod(modifier: TextSpanMod, from: Int = 0, to: Int = length): Spannable {
    modifier.typeface?.let { applyTypeface(it, from, to) }
    modifier.onClick?.let { applyClickable(it, from, to) }
    modifier.color?.let { applyColor(it, from, to) }
    modifier.underline?.let { applyUnderline(it, from, to) }
    modifier.round?.let { applyRounded(it, from, to) }
    modifier.superScript?.let { applySuperScript(it, from, to) }
    modifier.subScript?.let { applySubScript(it, from, to) }
    modifier.blur?.let {
        if(modifier.blurRadius!=null && modifier.blurStyle!=null) {
            applyBlur(it, modifier.blurRadius, modifier.blurStyle, from, to)
        } else if(modifier.blurRadius!=null) {
            applyBlur(it, radius = modifier.blurRadius, from = from, to = to)
        } else if(modifier.blurStyle!=null) {
            applyBlur(it, style = modifier.blurStyle, from = from, to = to)
        } else {
            applyBlur(it, from = from, to = to)
        }
    }
    modifier.relativeSize?.let { applyRelativeSize(it, from, to) }
    modifier.absoluteSize?.let { applyAbsoluteSize(it, modifier.absoluteSizeDip ?: true, from, to) }
    modifier.strike?.let { applyStrike(it, from, to) }
    modifier.drawable?.let {
        if(modifier.drawableWidth!=null && modifier.drawableHeight!=null) {
            applyDynamicDrawable(it, width = modifier.drawableWidth, height = modifier.drawableHeight, from = from, to = to)
        }else if(modifier.drawableWidth!=null){
            applyDynamicDrawable(it, width = modifier.drawableWidth, from = from, to = to)
        }else if(modifier.drawableHeight!=null){
            applyDynamicDrawable(it, height = modifier.drawableHeight, from = from, to = to)
        }else{
            applyDynamicDrawable(it, from = from, to = to)
        }
    }
    modifier.drawableResId?.let {
        if(modifier.drawableWidth!=null && modifier.drawableHeight!=null) {
            applyDynamicDrawable(it, width = modifier.drawableWidth, height = modifier.drawableHeight, from = from, to = to)
        }else if(modifier.drawableWidth!=null){
            applyDynamicDrawable(it, width = modifier.drawableWidth, from = from, to = to)
        }else if(modifier.drawableHeight!=null){
            applyDynamicDrawable(it, height = modifier.drawableHeight, from = from, to = to)
        }else{
            applyDynamicDrawable(it, from = from, to = to)
        }
    }
    modifier.bulletColor?.let {
        if(modifier.bulletRadius!=null && Build.VERSION.SDK_INT>=Build.VERSION_CODES.P && modifier.bulletGap!=null) {
            applyBullet(color = it, gap = modifier.bulletGap, radius = modifier.bulletRadius, from = from, to = to)
        } else if(modifier.bulletGap!=null) {
            applyBullet(color = it, gap = modifier.bulletGap, from = from, to = to)
        } else if(modifier.bulletRadius!=null && Build.VERSION.SDK_INT>=Build.VERSION_CODES.P) {
            applyBullet(color = it, radius = modifier.bulletRadius, from = from, to = to)
        } else {
            applyBullet(color = it, from = from, to = to)
        }
    }
    modifier.quoteColor?.let {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P && modifier.quoteGapWidth!=null && modifier.quoteStripeWidth!=null) {
            applyQuote(color = it, gapWidth = modifier.quoteGapWidth, stripeWidth = modifier.quoteStripeWidth, from = from, to = to)
        } else {
            applyQuote(color = it, from = from, to = to)
        }
    }
    modifier.suggestions?.let { applySuggestions(it, from = from, to = to) }
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

fun Spannable.applySuperScript(superScript: Boolean, from: Int = 0, to: Int = length): Spannable {
    if(superScript) setSpan(SuperscriptSpan(), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, SuperscriptSpan::class.java).forEach { removeSpan(it) }
    return this
}

fun Spannable.applySubScript(subScript: Boolean, from: Int = 0, to: Int = length): Spannable {
    if(subScript) setSpan(SubscriptSpan(), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, SubscriptSpan::class.java).forEach { removeSpan(it) }
    return this
}

/** Needs android:hardwareAccelerated="false" on manifest */
fun Spannable.applyBlur(blur: Boolean, radius: Float = 3f, style: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL, from: Int = 0, to: Int = length): Spannable {
    Log.d("applyBlur", "${substring(from, to)}, radius: $radius, style: $style")
    if(blur) setSpan(MaskFilterSpan(BlurMaskFilter(radius, style)), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    else getSpans(from, to, BlurMaskFilter::class.java).forEach { removeSpan(it) }
    return this
}

fun Spannable.applyAbsoluteSize(size: Int, dip: Boolean = true, from: Int = 0, to: Int = length): Spannable {
    setSpan(AbsoluteSizeSpan(size, dip), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * *WARNING* only works on EditText, not on TextView
 */
fun Spannable.applySuggestions(list: List<String>, flags: Int = SuggestionSpan.FLAG_EASY_CORRECT, from: Int = 0, to: Int = length): Spannable {
    setSpan(SuggestionSpan(InkSpannableConfig.context!!.get()!!, list.toTypedArray(), flags), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyDrawableMargin(drawable: Drawable, pad: Int, from: Int = 0, to: Int = length): Spannable {
    setSpan(DrawableMarginSpan(drawable, pad), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyDrawableMargin(resId: Int, pad: Int, from: Int = 0, to: Int = length): Spannable {
    val drawable = InkSpannableConfig.context!!.get()!!.resources.getDrawableCompat(resId)
    return applyDrawableMargin(drawable, pad, from, to)
}

private fun Resources.getDrawableCompat(resId: Int): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.getDrawable(resId, null)
    }else{
        this.getDrawable(resId)
    }
}

fun Spannable.applyDynamicDrawable(drawable: Drawable, width: Int? = null, height: Int? = null, alignment: Int = DynamicDrawableSpan.ALIGN_BASELINE, from: Int = 0, to: Int = length): Spannable {
    setSpan(DrawableDrawableSpan(drawable, width, height, alignment), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyDynamicDrawable(resId: Int, width: Int? = null, height: Int? = null, alignment: Int = DynamicDrawableSpan.ALIGN_BASELINE, from: Int = 0, to: Int = length): Spannable {
    setSpan(ResDrawableSpan(resId, width, height, alignment), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyQuote(color: Int, from: Int = 0, to: Int = length): Spannable {
    setSpan(QuoteSpan(color), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

@RequiresApi(Build.VERSION_CODES.P)
fun Spannable.applyQuote(color: Int, stripeWidth: Int, gapWidth: Int, from: Int = 0, to: Int = length): Spannable {
    setSpan(QuoteSpan(color, stripeWidth, gapWidth), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun Spannable.applyBullet(color: Int, gap: Int = 0, from: Int = 0, to: Int = length): Spannable {
    setSpan(BulletSpan(gap, color), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

@RequiresApi(Build.VERSION_CODES.P)
fun Spannable.applyBullet(color: Int, gap: Int = 0, radius: Int, from: Int = 0, to: Int = length): Spannable {
    setSpan(BulletSpan(gap, color, radius), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
