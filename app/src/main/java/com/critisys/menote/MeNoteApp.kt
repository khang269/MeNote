package com.critisys.menote

import android.app.Activity
import android.app.Application
import androidx.room.Room
import com.critisys.menote.data.database.MeNoteDatabase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@HiltAndroidApp
class MeNoteApp:Application()
