package uz.kozimjon.news.utils

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import io.paperdb.Paper
import uz.kozimjon.news.di.component.AppComponent
import uz.kozimjon.news.di.component.DaggerAppComponent
import java.util.*

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        Paper.init(this)
        appComponent = DaggerAppComponent.builder().build()
    }
}