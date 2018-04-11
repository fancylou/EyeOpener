package net.muliba.eyeopener

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.muliba.eyeopener.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frame_main_content, HomeFragment()).commit()

    }
}
