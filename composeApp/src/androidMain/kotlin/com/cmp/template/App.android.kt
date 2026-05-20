package com.cmp.template
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cmp.template.di.RootAppModule
import com.cmp.template.feature_auth.login.rememberChromeLauncher
import dev.theolm.rinku.Rinku
import dev.theolm.rinku.RinkuInit
import dev.theolm.rinku.compose.ext.Rinku
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(RootAppModule().module)
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RinkuInit()
        enableEdgeToEdge()
        setContent {
            Rinku() {
                RootApp()
                rememberChromeLauncher()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
