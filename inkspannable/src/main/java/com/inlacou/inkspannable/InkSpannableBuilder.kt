package com.inlacou.inkspannable

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.View

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
	
	fun addTextMod(string: String, modifier: TextSpanMod): InkSpannableBuilder {
		actions.add(Action(Type.ADD, modifier, listOf(string)))
		return this
	}
	
	private fun realAddTextMod(string: List<String>, modifier: TextSpanMod, builder: SpannableStringBuilder): InkSpannableBuilder {
		string.forEach { realAddTextMod(it, modifier, builder) }
		return this
	}
	
	private fun realAddTextMod(string: String, modifier: TextSpanMod, builder: SpannableStringBuilder): InkSpannableBuilder {
		val spannedString = SpannableString(string)
		modifier.typeface?.let { spannedString.applyTypeface(it, 0, string.length) }
		modifier.onClick?.let { spannedString.applyClickable(it, 0, string.length) }
		modifier.color?.let { spannedString.applyColor(it, 0, string.length) }
		modifier.underline?.let { spannedString.applyUnderline(it, 0, string.length) }
		builder.append(spannedString)
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
	
	fun underlineText(underline: Boolean = true, textToUnderline: String): InkSpannableBuilder = underlineText(underline, listOf(textToUnderline))
	fun underlineText(underline: Boolean = true, vararg toUnderlineText: String): InkSpannableBuilder = underlineText(underline, toUnderlineText.toList())
	fun underlineText(underline: Boolean = true, toUnderlineTextResId: Int): InkSpannableBuilder = underlineText(underline, InkSpannableConfig.instance.getString(toUnderlineTextResId))
	fun underlineText(underline: Boolean = true, toUnderlineResIds: Array<Int>): InkSpannableBuilder = underlineText(underline, toUnderlineResIds.map { InkSpannableConfig.instance.getString(it) })
	fun underlineText(underline: Boolean = true, vararg toUnderlineResIds: Int): InkSpannableBuilder = underlineText(underline, toUnderlineResIds.map { InkSpannableConfig.instance.getString(it) })
	fun underlineText(underline: Boolean = true, toUnderlineText: List<String>): InkSpannableBuilder = modText(TextSpanMod(underline = underline), toUnderlineText)
	
	fun colorText(color: Int, textToColor: String): InkSpannableBuilder = colorText(color, listOf(textToColor))
	fun colorText(color: Int, vararg toColorText: String): InkSpannableBuilder = colorText(color, toColorText.toList())
	fun colorText(color: Int, toColorTextResId: Int): InkSpannableBuilder = colorText(color, InkSpannableConfig.instance.getString(toColorTextResId))
	fun colorText(color: Int, toColorResIds: Array<Int>): InkSpannableBuilder = colorText(color, toColorResIds.map { InkSpannableConfig.instance.getString(it) })
	fun colorText(color: Int, vararg toColorResIds: Int): InkSpannableBuilder = colorText(color, toColorResIds.map { InkSpannableConfig.instance.getString(it) })
	fun colorText(color: Int, toColorText: List<String>): InkSpannableBuilder = modText(TextSpanMod(color = color), toColorText)
	
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
	
	private fun realModText(mod: TextSpanMod, toModText: List<String>, builder: SpannableStringBuilder): InkSpannableBuilder {
		toModText.forEach { textToMod ->
			if (!allText.contains(textToMod)) return@forEach
			
			val start = allText.indexOf(textToMod)
			val end = start + textToMod.length
			
			builder.applyMod(mod, start, end)
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
			if (it.type == Type.ADD) {
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