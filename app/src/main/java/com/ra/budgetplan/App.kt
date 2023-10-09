package com.ra.budgetplan

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.TimeZone

@HiltAndroidApp
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
  }
}