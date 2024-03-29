package com.inlacou.inkspannablelibraryproject

import android.app.Application
import android.content.res.Resources
import com.inlacou.inkspannable.InkSpannableConfig
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * Created by inlacou on 25/11/14.
 */
class AppCtrl : Application() {

	override fun onCreate() {
		super.onCreate()
		Timber.plant(Timber.DebugTree())

		// initialize the singleton
		instance = this
		
		InkSpannableConfig.context = WeakReference(this)
	}

	companion object {

		/**
		 * A singleton instance of the application class for easy access in other places
		 */
		lateinit var instance: AppCtrl
			private set

		fun generateCrash() {
			val strings = arrayOf("Hola", "Don", "Pepito")
			for (i in 0..19) {
				Timber.d(".generateCrash | string: ${strings[i]}")
			}
		}
	}
}

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
