package com.inlacou.inkspannablelibraryproject

import android.app.Application
import com.inlacou.inkspannable.InkSpannableConfig
import timber.log.Timber

/**
 * Created by inlacou on 25/11/14.
 */
class AppCtrl : Application() {

	override fun onCreate() {
		super.onCreate()
		Timber.plant(Timber.DebugTree())

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

		fun generateCrash() {
			val strings = arrayOf("Hola", "Don", "Pepito")
			for (i in 0..19) {
				Timber.d(".generateCrash | string: ${strings[i]}")
			}
		}
	}
}