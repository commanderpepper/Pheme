package com.commanderpepper.pheme.application

import android.app.Application
import android.util.Log
import com.commanderpepper.pheme.repository.local.NewsLocalDataSource
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PhemeApplication : Application() {

    @Inject
    lateinit var newsLocalDataSource: NewsLocalDataSource
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            try {
                /**
                 * When the application is first created delete articles to prevent too many articles from being stored in the local database
                 */
                newsLocalDataSource.deleteArticles()
            }
            catch (exception: Exception){
                Log.d(this@PhemeApplication::class.toString(), exception.message ?: "Unable to delete articles")
            }
        }

    }
}