package com.inlacou.inkspannable

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.View
import androidx.annotation.RequiresApi

/**
 * Created by inlacou on 26/09/17.
 */
class InkSpannableBuilder(private val allText: String) {
	
	constructor(allText: Int) : this(InkSpannableConfig.instance.getString(allText))
	constructor() : this("")
	
	private val actions: MutableList<Action> = mutableListOf()
	
	// From Secuential
	fun addText(string: String) = addTextMod(string, TextSpanMod())
	fun addText(stringResId: Int) = addTextMod(InkSpannableConfig.instance.getString(stringResId), TextSpanMod())
	
	fun addText(context: Context, resId: Int) = addTextMod(context.getString(resId), TextSpanMod())
	fun addTextStrike(string: String) = addTextMod(string, TextSpanMod(strike = true))
	fun addTextStrike(context: Context, resId: Int) = addTextMod(context.getString(resId), TextSpanMod(strike = true))
	fun addTextSuperScript(string: String) = addTextMod(string, TextSpanMod(superScript = true))
	fun addTextSuperScript(context: Context, resId: Int) = addTextMod(context.getString(resId), TextSpanMod(superScript = true))
	fun addTextSubScript(string: String) = addTextMod(string, TextSpanMod(subScript = true))
	fun addTextSubScript(context: Context, resId: Int) = addTextMod(context.getString(resId), TextSpanMod(subScript = true))
	/** Needs android:hardwareAccelerated="false" on your manifest */
	fun addTextBlur(string: String, radius: Float? = null, style: BlurMaskFilter.Blur? = null) = addTextMod(string, TextSpanMod(blur = true, blurRadius = radius, blurStyle = style))
	/** Needs android:hardwareAccelerated="false" on your manifest */
	fun addTextBlur(context: Context, resId: Int, radius: Float? = null, style: BlurMaskFilter.Blur? = null) = addTextMod(context.getString(resId), TextSpanMod(blur = true, blurRadius = radius, blurStyle = style))
	fun addTextRound(string: String) = addTextMod(string, TextSpanMod(round = true))
	fun addTextRound(context: Context, resId: Int) = addTextMod(context.getString(resId), TextSpanMod(round = true))
	fun addTextAbsoluteSize(string: String, size: Int, dip: Boolean) = addTextMod(string, TextSpanMod(absoluteSize = size, absoluteSizeDip = dip))
	fun addTextAbsoluteSize(context: Context, resId: Int, size: Int, dip: Boolean) = addTextMod(context.getString(resId), TextSpanMod(absoluteSize = size, absoluteSizeDip = dip))
	fun addTextRelativeSize(string: String, size: Float) = addTextMod(string, TextSpanMod(relativeSize = size))
	fun addTextRelativeSize(context: Context, resId: Int, size: Float) = addTextMod(context.getString(resId), TextSpanMod(relativeSize = size))
	fun addTextColor(string: String, color: Int) = addTextMod(string, TextSpanMod(color = color))
	fun addTextColor(context: Context, resId: Int, color: Int) = addTextMod(context.getString(resId), TextSpanMod(color = color))
	fun addTextBold(stringResId: Int): InkSpannableBuilder = addTextMod(InkSpannableConfig.instance.getString(stringResId), TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD))
	fun addTextBold(string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD))
	fun addTextBold(context: Context, resId: Int): InkSpannableBuilder = addTextMod(context.getString(resId), TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD))
	fun addTextItalic(string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(typeface = TextSpanMod.TextStyles.ITALIC))
	fun addTextItalic(context: Context, resId: Int): InkSpannableBuilder = addTextMod(context.getString(resId), TextSpanMod(typeface = TextSpanMod.TextStyles.ITALIC))
	fun addTextBoldItalic(string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD_ITALIC))
	fun addTextBoldItalic(context: Context, resId: Int): InkSpannableBuilder = addTextMod(context.getString(resId), TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD_ITALIC))
	fun addTextUnderline(string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(underline = true))
	fun addTextUnderline(context: Context, resId: Int): InkSpannableBuilder = addTextMod(context.getString(resId), TextSpanMod(underline = true))
	fun addTextUnderlineClickable(stringResId: Int, onClick: ((item: View?) -> Any)): InkSpannableBuilder = addTextMod(InkSpannableConfig.instance.getString(stringResId), TextSpanMod(underline = true, onClick = onClick))
	fun addTextUnderlineClickable(string: String, onClick: ((item: View?) -> Any)): InkSpannableBuilder = addTextMod(string, TextSpanMod(underline = true, onClick = onClick))
	fun addTextClickable(string: String, onClick: ((item: View?) -> Any)): InkSpannableBuilder = addTextMod(string, TextSpanMod(onClick = onClick))
	fun addTextClickable(context: Context, resId: Int, onClick: ((item: View?) -> Any)): InkSpannableBuilder = addTextMod(context.getString(resId), TextSpanMod(onClick = onClick))
	fun addTextBoldClickable(string: String, onClick: ((item: View?) -> Any)): InkSpannableBuilder = addTextMod(string, TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD, onClick = onClick))
	fun addTextBoldClickable(context: Context, resId: Int, onClick: ((item: View?) -> Any)): InkSpannableBuilder = addTextMod(context.getString(resId), TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD, onClick = onClick))
	fun addBlank(): InkSpannableBuilder = addTextMod(" ", TextSpanMod())
	/** bulletRadius will only work on Build.VERSION_CODES.P and onward */
	fun addBulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, string: String) = addTextMod(string, TextSpanMod(bulletColor = bulletColor, bulletGap = bulletGap, bulletRadius = bulletRadius))
	fun addBulletParagraph(bulletColor: Int, bulletGap: Int? = null, string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(bulletColor = bulletColor, bulletGap = bulletGap))
	fun addQuoteParagraph(quoteColor: Int, string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(quoteColor = quoteColor))
	/** gap and stripe will only work on Build.VERSION_CODES.P and onward */
	fun addQuoteParagraph(quoteColor: Int, gap: Int, stripe: Int, string: String): InkSpannableBuilder = addTextMod(string, TextSpanMod(quoteColor = quoteColor, quoteStripeWidth = stripe, quoteGapWidth = gap))
	fun addDrawable(resId: Int, width: Int? = null, height: Int? = null): InkSpannableBuilder = addTextMod("addDrawableByResId", TextSpanMod(drawableResId = resId, drawableWidth = width, drawableHeight = height))
	fun addDrawable(drawable: Drawable, width: Int? = null, height: Int? = null): InkSpannableBuilder = addTextMod("addDrawableByDrawable", TextSpanMod(drawable = drawable, drawableWidth = width, drawableHeight = height))
	
	fun addTextMod(string: String, modifier: TextSpanMod): InkSpannableBuilder {
		actions.add(Action(Type.ADD, modifier, listOf(string)))
		return this
	}
	
	private fun realAddTextMod(string: List<String>, modifier: TextSpanMod, builder: SpannableStringBuilder): InkSpannableBuilder {
		string.forEach { realAddTextMod(it, modifier, builder) }
		return this
	}
	
	private fun realAddTextMod(string: String, modifier: TextSpanMod, builder: SpannableStringBuilder): InkSpannableBuilder {
		builder.append(SpannableString(string).applyMod(modifier))
		return this
	}
	/// From Secuential
	
	fun boldText(textToBold: String): InkSpannableBuilder = boldText(listOf(textToBold))
	fun boldText(vararg toBoldText: String): InkSpannableBuilder = boldText(toBoldText.toList())
	fun boldText(toBoldTextResId: Int): InkSpannableBuilder = boldText(InkSpannableConfig.instance.getString(toBoldTextResId))
	fun boldText(toBoldResIds: Array<Int>): InkSpannableBuilder = boldText(toBoldResIds.map { InkSpannableConfig.instance.getString(it) })
	fun boldText(vararg toBoldResIds: Int): InkSpannableBuilder = boldText(toBoldResIds.map { InkSpannableConfig.instance.getString(it) })
	fun boldText(toBoldText: List<String>): InkSpannableBuilder = modText(TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD), toBoldText)
	
	fun italicText(textToItalic: String): InkSpannableBuilder = italicText(listOf(textToItalic))
	fun italicText(vararg toItalicText: String): InkSpannableBuilder = italicText(toItalicText.toList())
	fun italicText(toItalicTextResId: Int): InkSpannableBuilder = italicText(InkSpannableConfig.instance.getString(toItalicTextResId))
	fun italicText(toItalicResIds: Array<Int>): InkSpannableBuilder = italicText(toItalicResIds.map { InkSpannableConfig.instance.getString(it) })
	fun italicText(vararg toItalicResIds: Int): InkSpannableBuilder = italicText(toItalicResIds.map { InkSpannableConfig.instance.getString(it) })
	fun italicText(toItalicText: List<String>): InkSpannableBuilder = modText(TextSpanMod(typeface = TextSpanMod.TextStyles.ITALIC), toItalicText)
	
	fun boldItalicText(textToBoldItalic: String): InkSpannableBuilder = boldItalicText(listOf(textToBoldItalic))
	fun boldItalicText(vararg toBoldItalicText: String): InkSpannableBuilder = boldItalicText(toBoldItalicText.toList())
	fun boldItalicText(toBoldItalicTextResId: Int): InkSpannableBuilder = boldItalicText(InkSpannableConfig.instance.getString(toBoldItalicTextResId))
	fun boldItalicText(toBoldItalicResIds: Array<Int>): InkSpannableBuilder = boldItalicText(toBoldItalicResIds.map { InkSpannableConfig.instance.getString(it) })
	fun boldItalicText(vararg toBoldItalicResIds: Int): InkSpannableBuilder = boldItalicText(toBoldItalicResIds.map { InkSpannableConfig.instance.getString(it) })
	fun boldItalicText(toBoldItalicText: List<String>): InkSpannableBuilder = modText(TextSpanMod(typeface = TextSpanMod.TextStyles.BOLD_ITALIC), toBoldItalicText)
	
	fun strikeText(strike: Boolean = true, textToStrike: String): InkSpannableBuilder = strikeText(strike, listOf(textToStrike))
	fun strikeText(strike: Boolean = true, vararg toStrikeText: String): InkSpannableBuilder = strikeText(strike, toStrikeText.toList())
	fun strikeText(strike: Boolean = true, toStrikeTextResId: Int): InkSpannableBuilder = strikeText(strike, InkSpannableConfig.instance.getString(toStrikeTextResId))
	fun strikeText(strike: Boolean = true, toStrikeResIds: Array<Int>): InkSpannableBuilder = strikeText(strike, toStrikeResIds.map { InkSpannableConfig.instance.getString(it) })
	fun strikeText(strike: Boolean = true, vararg toStrikeResIds: Int): InkSpannableBuilder = strikeText(strike, toStrikeResIds.map { InkSpannableConfig.instance.getString(it) })
	fun strikeText(strike: Boolean = true, toStrikeText: List<String>): InkSpannableBuilder = modText(TextSpanMod(strike = strike), toStrikeText)
	
	fun superScriptText(superScript: Boolean = true, textToSuperScript: String): InkSpannableBuilder = superScriptText(superScript, listOf(textToSuperScript))
	fun superScriptText(superScript: Boolean = true, vararg toSuperScriptText: String): InkSpannableBuilder = superScriptText(superScript, toSuperScriptText.toList())
	fun superScriptText(superScript: Boolean = true, toSuperScriptTextResId: Int): InkSpannableBuilder = superScriptText(superScript, InkSpannableConfig.instance.getString(toSuperScriptTextResId))
	fun superScriptText(superScript: Boolean = true, toSuperScriptResIds: Array<Int>): InkSpannableBuilder = superScriptText(superScript, toSuperScriptResIds.map { InkSpannableConfig.instance.getString(it) })
	fun superScriptText(superScript: Boolean = true, vararg toSuperScriptResIds: Int): InkSpannableBuilder = superScriptText(superScript, toSuperScriptResIds.map { InkSpannableConfig.instance.getString(it) })
	fun superScriptText(superScript: Boolean = true, toSuperScriptText: List<String>): InkSpannableBuilder = modText(TextSpanMod(superScript = superScript), toSuperScriptText)
	
	fun subScriptText(subScript: Boolean = true, textToSuperScript: String): InkSpannableBuilder = subScriptText(subScript, listOf(textToSuperScript))
	fun subScriptText(subScript: Boolean = true, vararg toSuperScriptText: String): InkSpannableBuilder = subScriptText(subScript, toSuperScriptText.toList())
	fun subScriptText(subScript: Boolean = true, toSuperScriptTextResId: Int): InkSpannableBuilder = subScriptText(subScript, InkSpannableConfig.instance.getString(toSuperScriptTextResId))
	fun subScriptText(subScript: Boolean = true, toSuperScriptResIds: Array<Int>): InkSpannableBuilder = subScriptText(subScript, toSuperScriptResIds.map { InkSpannableConfig.instance.getString(it) })
	fun subScriptText(subScript: Boolean = true, vararg toSuperScriptResIds: Int): InkSpannableBuilder = subScriptText(subScript, toSuperScriptResIds.map { InkSpannableConfig.instance.getString(it) })
	fun subScriptText(subScript: Boolean = true, toSuperScriptText: List<String>): InkSpannableBuilder = modText(TextSpanMod(subScript = subScript), toSuperScriptText)
	
	fun blurText(blur: Boolean = true, radius: Float? = null, style: BlurMaskFilter.Blur? = null, textToSuperScript: String): InkSpannableBuilder = blurText(blur, radius, style, listOf(textToSuperScript))
	fun blurText(blur: Boolean = true, radius: Float? = null, style: BlurMaskFilter.Blur? = null, vararg toSuperScriptText: String): InkSpannableBuilder = blurText(blur, radius, style, toSuperScriptText.toList())
	fun blurText(blur: Boolean = true, radius: Float? = null, style: BlurMaskFilter.Blur? = null, toSuperScriptTextResId: Int): InkSpannableBuilder = blurText(blur, radius, style, InkSpannableConfig.instance.getString(toSuperScriptTextResId))
	fun blurText(blur: Boolean = true, radius: Float? = null, style: BlurMaskFilter.Blur? = null, toSuperScriptResIds: Array<Int>): InkSpannableBuilder = blurText(blur, radius, style, toSuperScriptResIds.map { InkSpannableConfig.instance.getString(it) })
	fun blurText(blur: Boolean = true, radius: Float? = null, style: BlurMaskFilter.Blur? = null, vararg toSuperScriptResIds: Int): InkSpannableBuilder = blurText(blur, radius, style, toSuperScriptResIds.map { InkSpannableConfig.instance.getString(it) })
	fun blurText(blur: Boolean = true, radius: Float? = null, style: BlurMaskFilter.Blur? = null, toSuperScriptText: List<String>): InkSpannableBuilder = modText(TextSpanMod(blur = blur, blurRadius = radius, blurStyle = style), toSuperScriptText)
	
	fun underlineText(underline: Boolean = true, textToUnderline: String): InkSpannableBuilder = underlineText(underline, listOf(textToUnderline))
	fun underlineText(underline: Boolean = true, vararg toUnderlineText: String): InkSpannableBuilder = underlineText(underline, toUnderlineText.toList())
	fun underlineText(underline: Boolean = true, toUnderlineTextResId: Int): InkSpannableBuilder = underlineText(underline, InkSpannableConfig.instance.getString(toUnderlineTextResId))
	fun underlineText(underline: Boolean = true, toUnderlineResIds: Array<Int>): InkSpannableBuilder = underlineText(underline, toUnderlineResIds.map { InkSpannableConfig.instance.getString(it) })
	fun underlineText(underline: Boolean = true, vararg toUnderlineResIds: Int): InkSpannableBuilder = underlineText(underline, toUnderlineResIds.map { InkSpannableConfig.instance.getString(it) })
	fun underlineText(underline: Boolean = true, toUnderlineText: List<String>): InkSpannableBuilder = modText(TextSpanMod(underline = underline), toUnderlineText)
	
	fun roundText(round: Boolean = true, textToRound: String): InkSpannableBuilder = roundText(round, listOf(textToRound))
	fun roundText(round: Boolean = true, vararg toRoundText: String): InkSpannableBuilder = roundText(round, toRoundText.toList())
	fun roundText(round: Boolean = true, toRoundTextResId: Int): InkSpannableBuilder = roundText(round, InkSpannableConfig.instance.getString(toRoundTextResId))
	fun roundText(round: Boolean = true, toRoundResIds: Array<Int>): InkSpannableBuilder = roundText(round, toRoundResIds.map { InkSpannableConfig.instance.getString(it) })
	fun roundText(round: Boolean = true, vararg toRoundResIds: Int): InkSpannableBuilder = roundText(round, toRoundResIds.map { InkSpannableConfig.instance.getString(it) })
	fun roundText(round: Boolean = true, toRoundText: List<String>): InkSpannableBuilder = modText(TextSpanMod(round = round), toRoundText)
	
	fun relativeSizeText(relativeSize: Float, textToChangeSize: String): InkSpannableBuilder = relativeSizeText(relativeSize, listOf(textToChangeSize))
	fun relativeSizeText(relativeSize: Float, vararg toChangeSizeText: String): InkSpannableBuilder = relativeSizeText(relativeSize, toChangeSizeText.toList())
	fun relativeSizeText(relativeSize: Float, toChangeSizeTextResId: Int): InkSpannableBuilder = relativeSizeText(relativeSize, InkSpannableConfig.instance.getString(toChangeSizeTextResId))
	fun relativeSizeText(relativeSize: Float, toChangeSizeResIds: Array<Int>): InkSpannableBuilder = relativeSizeText(relativeSize, toChangeSizeResIds.map { InkSpannableConfig.instance.getString(it) })
	fun relativeSizeText(relativeSize: Float, vararg toChangeSizeResIds: Int): InkSpannableBuilder = relativeSizeText(relativeSize, toChangeSizeResIds.map { InkSpannableConfig.instance.getString(it) })
	fun relativeSizeText(relativeSize: Float, toChangeSizeText: List<String>): InkSpannableBuilder = modText(TextSpanMod(relativeSize = relativeSize), toChangeSizeText)
	
	fun absoluteSizeText(absoluteSize: Int, dip: Boolean = true, textToChangeSize: String): InkSpannableBuilder = absoluteSizeText(absoluteSize, dip, listOf(textToChangeSize))
	fun absoluteSizeText(absoluteSize: Int, dip: Boolean = true, vararg toChangeSizeText: String): InkSpannableBuilder = absoluteSizeText(absoluteSize, dip, toChangeSizeText.toList())
	fun absoluteSizeText(absoluteSize: Int, dip: Boolean = true, toChangeSizeTextResId: Int): InkSpannableBuilder = absoluteSizeText(absoluteSize, dip, InkSpannableConfig.instance.getString(toChangeSizeTextResId))
	fun absoluteSizeText(absoluteSize: Int, dip: Boolean = true, toChangeSizeResIds: Array<Int>): InkSpannableBuilder = absoluteSizeText(absoluteSize, dip, toChangeSizeResIds.map { InkSpannableConfig.instance.getString(it) })
	fun absoluteSizeText(absoluteSize: Int, dip: Boolean = true, vararg toChangeSizeResIds: Int): InkSpannableBuilder = absoluteSizeText(absoluteSize, dip, toChangeSizeResIds.map { InkSpannableConfig.instance.getString(it) })
	fun absoluteSizeText(absoluteSize: Int, dip: Boolean = true, toChangeSizeText: List<String>): InkSpannableBuilder = modText(TextSpanMod(absoluteSize = absoluteSize, absoluteSizeDip = dip), toChangeSizeText)
	
	fun colorText(color: Int, textToColor: String): InkSpannableBuilder = colorText(color, listOf(textToColor))
	fun colorText(color: Int, vararg toColorText: String): InkSpannableBuilder = colorText(color, toColorText.toList())
	fun colorText(color: Int, toColorTextResId: Int): InkSpannableBuilder = colorText(color, InkSpannableConfig.instance.getString(toColorTextResId))
	fun colorText(color: Int, toColorResIds: Array<Int>): InkSpannableBuilder = colorText(color, toColorResIds.map { InkSpannableConfig.instance.getString(it) })
	fun colorText(color: Int, vararg toColorResIds: Int): InkSpannableBuilder = colorText(color, toColorResIds.map { InkSpannableConfig.instance.getString(it) })
	fun colorText(color: Int, toColorText: List<String>): InkSpannableBuilder = modText(TextSpanMod(color = color), toColorText)
	
	fun drawable(resId: Int, width: Int? = null, height: Int? = null, textToChangeForDrawable: String): InkSpannableBuilder = drawable(resId, width, height, listOf(textToChangeForDrawable))
	fun drawable(resId: Int, width: Int? = null, height: Int? = null, vararg toChangeForDrawableText: String): InkSpannableBuilder = drawable(resId, width, height, toChangeForDrawableText.toList())
	fun drawable(resId: Int, width: Int? = null, height: Int? = null, toChangeForDrawableTextResId: Int): InkSpannableBuilder = drawable(resId, width, height, InkSpannableConfig.instance.getString(toChangeForDrawableTextResId))
	fun drawable(resId: Int, width: Int? = null, height: Int? = null, toChangeForDrawableResIds: Array<Int>): InkSpannableBuilder = drawable(resId, width, height, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	fun drawable(resId: Int, width: Int? = null, height: Int? = null, vararg toChangeForDrawableResIds: Int): InkSpannableBuilder = drawable(resId, width, height, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	fun drawable(resId: Int, width: Int? = null, height: Int? = null, toChangeForDrawableText: List<String>): InkSpannableBuilder = modText(TextSpanMod(drawableResId = resId, drawableWidth = width, drawableHeight = height), toChangeForDrawableText)
	
	fun drawable(drawable: Drawable, width: Int? = null, height: Int? = null, textToChangeForDrawable: String): InkSpannableBuilder = drawable(drawable, width, height, listOf(textToChangeForDrawable))
	fun drawable(drawable: Drawable, width: Int? = null, height: Int? = null, vararg toChangeForDrawableText: String): InkSpannableBuilder = drawable(drawable, width, height, toChangeForDrawableText.toList())
	fun drawable(drawable: Drawable, width: Int? = null, height: Int? = null, toChangeForDrawableTextResId: Int): InkSpannableBuilder = drawable(drawable, width, height, InkSpannableConfig.instance.getString(toChangeForDrawableTextResId))
	fun drawable(drawable: Drawable, width: Int? = null, height: Int? = null, toChangeForDrawableResIds: Array<Int>): InkSpannableBuilder = drawable(drawable, width, height, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	fun drawable(drawable: Drawable, width: Int? = null, height: Int? = null, vararg toChangeForDrawableResIds: Int): InkSpannableBuilder = drawable(drawable, width, height, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	fun drawable(drawable: Drawable, width: Int? = null, height: Int? = null, toChangeForDrawableText: List<String>): InkSpannableBuilder = modText(TextSpanMod(drawable = drawable, drawableWidth = width, drawableHeight = height), toChangeForDrawableText)
	
	@RequiresApi(Build.VERSION_CODES.P)
	fun quoteParagraph(quoteColor: Int, gap: Int, stripe: Int, textToChangeForDrawable: String): InkSpannableBuilder = quoteParagraph(quoteColor, gap, stripe, listOf(textToChangeForDrawable))
	@RequiresApi(Build.VERSION_CODES.P)
	fun quoteParagraph(quoteColor: Int, gap: Int, stripe: Int, vararg toChangeForDrawableText: String): InkSpannableBuilder = quoteParagraph(quoteColor, gap, stripe, toChangeForDrawableText.toList())
	@RequiresApi(Build.VERSION_CODES.P)
	fun quoteParagraph(quoteColor: Int, gap: Int, stripe: Int, toChangeForDrawableTextResId: Int): InkSpannableBuilder = quoteParagraph(quoteColor, gap, stripe, InkSpannableConfig.instance.getString(toChangeForDrawableTextResId))
	@RequiresApi(Build.VERSION_CODES.P)
	fun quoteParagraph(quoteColor: Int, gap: Int, stripe: Int, toChangeForDrawableResIds: Array<Int>): InkSpannableBuilder = quoteParagraph(quoteColor, gap, stripe, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	@RequiresApi(Build.VERSION_CODES.P)
	fun quoteParagraph(quoteColor: Int, gap: Int, stripe: Int, vararg toChangeForDrawableResIds: Int): InkSpannableBuilder = quoteParagraph(quoteColor, gap, stripe, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	@RequiresApi(Build.VERSION_CODES.P)
	fun quoteParagraph(quoteColor: Int, gap: Int, stripe: Int, toChangeForDrawableText: List<String>): InkSpannableBuilder = modText(TextSpanMod(quoteColor = quoteColor, quoteGapWidth = gap, quoteStripeWidth = stripe), toChangeForDrawableText)
	
	fun quoteParagraph(quoteColor: Int, textToChangeForDrawable: String): InkSpannableBuilder = quoteParagraph(quoteColor, listOf(textToChangeForDrawable))
	fun quoteParagraph(quoteColor: Int, vararg toChangeForDrawableText: String): InkSpannableBuilder = quoteParagraph(quoteColor, toChangeForDrawableText.toList())
	fun quoteParagraph(quoteColor: Int, toChangeForDrawableTextResId: Int): InkSpannableBuilder = quoteParagraph(quoteColor, InkSpannableConfig.instance.getString(toChangeForDrawableTextResId))
	fun quoteParagraph(quoteColor: Int, toChangeForDrawableResIds: Array<Int>): InkSpannableBuilder = quoteParagraph(quoteColor, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	fun quoteParagraph(quoteColor: Int, vararg toChangeForDrawableResIds: Int): InkSpannableBuilder = quoteParagraph(quoteColor, toChangeForDrawableResIds.map { InkSpannableConfig.instance.getString(it) })
	fun quoteParagraph(quoteColor: Int, toChangeForDrawableText: List<String>): InkSpannableBuilder = modText(TextSpanMod(quoteColor = quoteColor), toChangeForDrawableText)
	
	@RequiresApi(Build.VERSION_CODES.P)
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, textToIdentifyParagraph: String): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, bulletRadius, listOf(textToIdentifyParagraph))
	@RequiresApi(Build.VERSION_CODES.P)
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, vararg toIdentifyParagraphText: String): InkSpannableBuilder = bulletParagraph(bulletColor, bulletRadius, bulletGap, toIdentifyParagraphText.toList())
	@RequiresApi(Build.VERSION_CODES.P)
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, toIdentifyParagraphTextResId: Int): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, bulletRadius, InkSpannableConfig.instance.getString(toIdentifyParagraphTextResId))
	@RequiresApi(Build.VERSION_CODES.P)
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, toIdentifyParagraphResIds: Array<Int>): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, bulletRadius, toIdentifyParagraphResIds.map { InkSpannableConfig.instance.getString(it) })
	@RequiresApi(Build.VERSION_CODES.P)
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, vararg toIdentifyParagraphResIds: Int): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, bulletRadius, toIdentifyParagraphResIds.map { InkSpannableConfig.instance.getString(it) })
	@RequiresApi(Build.VERSION_CODES.P)
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, bulletRadius: Int? = null, toIdentifyParagraphText: List<String>): InkSpannableBuilder = modText(TextSpanMod(bulletColor = bulletColor, bulletGap = bulletGap, bulletRadius = bulletRadius), toIdentifyParagraphText)
	
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, textToIdentifyParagraph: String): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, listOf(textToIdentifyParagraph))
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, vararg toIdentifyParagraphText: String): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, toIdentifyParagraphText.toList())
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, toIdentifyParagraphTextResId: Int): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, InkSpannableConfig.instance.getString(toIdentifyParagraphTextResId))
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, toIdentifyParagraphResIds: Array<Int>): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, toIdentifyParagraphResIds.map { InkSpannableConfig.instance.getString(it) })
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, vararg toIdentifyParagraphResIds: Int): InkSpannableBuilder = bulletParagraph(bulletColor, bulletGap, toIdentifyParagraphResIds.map { InkSpannableConfig.instance.getString(it) })
	fun bulletParagraph(bulletColor: Int, bulletGap: Int? = null, toIdentifyParagraphText: List<String>): InkSpannableBuilder = modText(TextSpanMod(bulletColor = bulletColor, bulletGap = bulletGap), toIdentifyParagraphText)
	
	/** Do not forget to set movementMethod = LinkMovementMethod()
	 * This will change span color to whatever textColorLink and underline it, but you can override after it */
	fun clickableText(clickable: ((item: View?) -> Any)?, textToClickable: String): InkSpannableBuilder = clickableText(clickable, listOf(textToClickable))
	
	/** Do not forget to set movementMethod = LinkMovementMethod()
	 * This will change span color to whatever textColorLink and underline it, but you can override after it */
	fun clickableText(clickable: ((item: View?) -> Any)?, vararg toClickableText: String): InkSpannableBuilder = clickableText(clickable, toClickableText.toList())
	
	/** Do not forget to set movementMethod = LinkMovementMethod()
	 * This will change span color to whatever textColorLink and underline it, but you can override after it */
	fun clickableText(clickable: ((item: View?) -> Any)?, toClickableTextResId: Int): InkSpannableBuilder = clickableText(clickable, InkSpannableConfig.instance.getString(toClickableTextResId))
	
	/** Do not forget to set movementMethod = LinkMovementMethod()
	 * This will change span color to whatever textColorLink and underline it, but you can override after it */
	fun clickableText(clickable: ((item: View?) -> Any)?, toClickableResIds: Array<Int>): InkSpannableBuilder = clickableText(clickable, toClickableResIds.map { InkSpannableConfig.instance.getString(it) })
	
	/** Do not forget to set movementMethod = LinkMovementMethod()
	 * This will change span color to whatever textColorLink and underline it, but you can override after it */
	fun clickableText(clickable: ((item: View?) -> Any)?, vararg toClickableResIds: Int): InkSpannableBuilder = clickableText(clickable, toClickableResIds.map { InkSpannableConfig.instance.getString(it) })
	
	/** Do not forget to set movementMethod = LinkMovementMethod()
	 * This will change span color to whatever textColorLink and underline it, but you can override after it */
	fun clickableText(clickable: ((item: View?) -> Any)?, toClickableText: List<String>): InkSpannableBuilder = modText(TextSpanMod(onClick = clickable), toClickableText)
	
	fun modText(mod: TextSpanMod, textToMod: String): InkSpannableBuilder = modText(mod, listOf(textToMod))
	fun modText(mod: TextSpanMod, vararg toModText: String): InkSpannableBuilder = modText(mod, toModText.toList())
	fun modText(mod: TextSpanMod, toModTextResId: Int): InkSpannableBuilder = modText(mod, InkSpannableConfig.instance.getString(toModTextResId))
	fun modText(mod: TextSpanMod, toModResIds: Array<Int>): InkSpannableBuilder = modText(mod, toModResIds.map { InkSpannableConfig.instance.getString(it) })
	fun modText(mod: TextSpanMod, vararg toModResIds: Int): InkSpannableBuilder = modText(mod, toModResIds.map { InkSpannableConfig.instance.getString(it) })
	fun modText(mod: TextSpanMod, toModText: List<String>): InkSpannableBuilder {
		actions.add(Action(Type.EDIT, mod, toModText))
		return this
	}
	
	private fun CharSequence.indexesOf(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): List<Int> {
		val indexes = mutableListOf<Int>()
		var lastIndex = indexOf(string, startIndex, ignoreCase)
		while (lastIndex!=-1) {
			indexes.add(lastIndex)
			lastIndex = indexOf(string, lastIndex+1, ignoreCase)
		}
		
		return indexes
	}
	
	private fun realModText(mod: TextSpanMod, toModText: List<String>, builder: SpannableStringBuilder): InkSpannableBuilder {
		toModText.forEach { textToMod ->
			if (!allText.contains(textToMod)) return@forEach
			
			allText.indexesOf(textToMod).map { Pair(it, it+textToMod.length) }.forEach {
				builder.applyMod(mod, it.first, it.second)
			}
		}
		return this
	}
	
	private class Action(val type: Type, val mod: TextSpanMod, val toModText: List<String>)
	
	enum class Type {
		ADD, EDIT
	}
	
	fun build(): SpannableStringBuilder {
		val builder = SpannableStringBuilder(allText)
		actions.forEach {
			if (it.type==Type.ADD) {
				realAddTextMod(it.toModText, it.mod, builder)
			} else {
				realModText(it.mod, it.toModText, builder)
			}
		}
		return builder
	}
	
	override fun toString(): String {
		return build().toString()
	}
}