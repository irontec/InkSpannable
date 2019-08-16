# InkSpannable
[![](https://jitpack.io/v/irontec/InkSpannable.svg)](https://jitpack.io/#irontec/InkSpannable)

Better spannable creation.

## Configuration

First of all you need to pass the Application context to the InkSpannableConfig:

```kt
class AppCtrl : Application() {

	override fun onCreate() {
		super.onCreate()

		// initialize the singleton
		instance = this
		
		InkSpannableConfig.context = this
	}

	companion object {

		/**
		 * A singleton instance of the application class for easy access in other places
		 */
		lateinit var instance: AppCtrl
			private set
	}
}
```

Then you can create an InkSpannable with some text or empty, and modify it. It has methods available to add text (styled if you want, string or string resource id) or to modify current text style. Then you build it and assign it to a TextView or some other text holder which supports SpannableStringBuilder.

## Example

Build some long text:
```kt
textview.text = InkSpannableBuilder().addText("long")
						.addBlank().addTextItalic("long")
						.addBlank().addTextBoldItalic("complex")
						.addBlank().addTextColor("text", getColorCompat(R.color.basic_red))
						.addBlank().addTextColor("I", getColorCompat(R.color.basic_green))
						.addBlank().addTextColor("am", getColorCompat(R.color.basic_blue))
						.addBlank().addTextUnderline("testing")
						.build()
```

Create with text and then modify it:
```kt
textview.text = InkSpannableBuilder(R.string.long_text_1)
			.boldText(AppCtrl.instance.resources.getStringArray(R.array.long_text_1_bold).toList())
```

With resources from strings.xml:
```xml
	<string name="long_text_1">We define some words to bold on an array in Strings.xml, like this one or this other one</string>

	<string-array name="long_text_1_bold">
		<item>Strings.xml</item>
		<item>this one</item>
		<item>this other one</item>
	</string-array>
```
