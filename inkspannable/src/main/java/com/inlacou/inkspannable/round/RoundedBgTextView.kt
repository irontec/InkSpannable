/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.inlacou.inkspannable.round

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import androidx.core.graphics.withTranslation
import com.inlacou.inkspannable.ExtensibleTextView

/**
 * A TextView that can drawable rounded background to the portions of the text. See
 * [TextRoundedBgHelper] for more information.
 *
 * See [TextRoundedBgAttributeReader] for supported attributes.
 */
class RoundedBgTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : ExtensibleTextView(context, attrs, defStyleAttr) {
	
	private val textRoundedBgHelper: TextRoundedBgHelper
	
	init {
		val attributeReader = TextRoundedBgAttributeReader(context, attrs)
		textRoundedBgHelper = TextRoundedBgHelper(
				horizontalPadding = attributeReader.horizontalPadding,
				verticalPadding = attributeReader.verticalPadding,
				drawable = attributeReader.drawable,
				drawableLeft = attributeReader.drawableLeft,
				drawableMid = attributeReader.drawableMid,
				drawableRight = attributeReader.drawableRight
		)
		drawInterceptors.add { canvas ->
			canvas.withTranslation(totalPaddingLeft.toFloat(), totalPaddingTop.toFloat()) {
				textRoundedBgHelper.draw(canvas, text as Spanned, layout)
			}
		}
	}
}