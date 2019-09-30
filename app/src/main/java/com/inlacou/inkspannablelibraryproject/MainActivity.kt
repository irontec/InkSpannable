package com.inlacou.inkspannablelibraryproject

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.inlacou.inkspannable.*
import com.inlacou.inkspannable.round.RoundedBgTextView
import timber.log.Timber

class MainActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		findViewById<LinearLayout>(R.id.ll).apply {
			addView(TextView(context).apply {
				text = "normal"
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder().addText("long")
						.addBlank().addTextItalic("long")
						.addBlank().addTextBoldItalic("complex")
						.addBlank().addTextColor("text", getColorCompat(R.color.basic_red))
						.addBlank().addTextColor("I", getColorCompat(R.color.basic_green))
						.addBlank().addTextColor("am", getColorCompat(R.color.basic_blue))
						.addBlank().addTextUnderline("testing")
						.build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder().addText("normal").addBlank().addTextBold("bold").build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder("normal bold").boldText("bold").addBlank().addText("(other way)").build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder().addText("normal").addBlank().addTextItalic("italic").build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder("normal italic").italicText("italic").addBlank().addText("(other way)").build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder("normal bold italic").boldText("bold").italicText("italic").build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder("normal bolditalic").boldItalicText("bolditalic").build()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder().addText("normal").addBlank().addTextBoldItalic("bolditalic").addBlank().addText("(other way)").build()
			})
			addView(RoundedBgTextView(context).apply {
				text = InkSpannableBuilder("normal text text to bold text to red clickable text")
						.boldText("text to bold")
						.colorText(getColorCompat(R.color.basic_red), "text to red")
						.colorText(getColorCompat(R.color.basic_blue), "bold text")
						.roundText(true, "to red")
						.clickableText({
							Toast.makeText(this@MainActivity, "clicked!", Toast.LENGTH_SHORT).show()
						}, "clickable text")
						.colorText(getColorCompat(R.color.basic_black), "clickable text")
						.underlineText(false, "clickable text")
						.build()
				movementMethod = LinkMovementMethod()
			})
			addView(TextView(context).apply {
				Timber.d("prebuild1: $theSameButPreBuilt1")
				text = theSameButPreBuilt1.build()
				movementMethod = LinkMovementMethod()
			})
			addView(TextView(context).apply {
				text = InkSpannableBuilder(R.string.long_text_1)
						.boldText(resources.getStringArray(R.array.long_text_1_bold).toList())
						.build()
			})
			addView(RoundedBgTextView(context).apply {
				Timber.d("prebuild2: $theSameButPreBuilt2")
				text = theSameButPreBuilt2.addBlank().addText("(other way)").addBlank().addTextRound("prueba").build()
			})
			addView(RoundedBgTextView(context).apply {
				text = InkSpannableBuilder("absoluteSize relativeSize round strike superscript absoluteSize relativeSize")
						.roundText(textToRound = "round")
						.strikeText(textToStrike = "strike")
						.superScriptText(textToSuperScript = "superscript")
						.superScriptText(textToSuperScript = "script")
						.absoluteSizeText(24, true, "absoluteSize")
						.relativeSizeText(2f, "relativeSize")
						.build()
			})
			addView(RoundedBgTextView(context).apply {
				text = InkSpannableBuilder("some text bla blastrike superscript round drawable some more text justo to make it multiple lines bla bla bla\nnew paragraph la la\nanother new one")
						.roundText(textToRound = "round")
						.strikeText(textToStrike = "strike")
						.superScriptText(textToSuperScript = "superscript")
						.superScriptText(textToSuperScript = "script")
						.bulletParagraph(getColorCompat(R.color.basic_red), 10, "new paragraph")
						.quoteParagraph(getColorCompat(R.color.basic_red), "another new")
						.drawable(R.drawable.space_invader, 16.dpToPx(), 16.dpToPx(), "drawable")
						.build()
			})
			addView(RoundedBgTextView(context).apply {
				text = InkSpannableBuilder("add drawable dynamically")
						.addDrawable(R.drawable.space_invader, 16.dpToPx(), 16.dpToPx())
						.build()
			})
		}
	}
	
	private val theSameButPreBuilt1 = InkSpannableBuilder("normal text text to bold text to red clickable text")
			.boldText("text to bold")
			.colorText(AppCtrl.instance.getColorCompat(R.color.basic_red), "text to red")
			.colorText(AppCtrl.instance.getColorCompat(R.color.basic_blue), "bold text")
			.colorText(AppCtrl.instance.getColorCompat(R.color.basic_black), "blue text")
			.underlineText(false, "underline text")
	
	private val theSameButPreBuilt2 = InkSpannableBuilder(R.string.long_text_1)
			.boldText(AppCtrl.instance.resources.getStringArray(R.array.long_text_1_bold).toList())
	
	
	private fun Context.getColorCompat(resId: Int): Int {
		return resources.getColorCompat(resId)
	}
	
	private fun Resources.getColorCompat(resId: Int): Int {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getColor(resId, null)
		}else{
			getColor(resId)
		}
	}
	
}
