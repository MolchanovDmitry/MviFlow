package dmitry.molchanov.mvi_kotlin_app.start

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dmitry.molchanov.mvi_kotlin_app.MainActivity
import dmitry.molchanov.mvi_kotlin_app.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<View>(R.id.start_button).setOnClickListener {
            Intent(this, MainActivity::class.java)
                .let(::startActivity)
        }
    }
}