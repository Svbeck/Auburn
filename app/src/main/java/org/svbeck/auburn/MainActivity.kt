package org.svbeck.auburn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.svbeck.auburn.ui.main.MovieListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieListFragment.newInstance())
                    .commitNow()
        }
    }

}
