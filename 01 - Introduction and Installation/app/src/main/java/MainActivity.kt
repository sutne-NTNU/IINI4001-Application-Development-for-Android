package com.example.oving_1

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(meny: Menu): Boolean {
        super.onCreateOptionsMenu(meny)
        meny.add("Sivert")
        meny.add("Utne")
        Log.i("onCreateOptionsMenu()", "Øving 1 - Opprettet meny")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Sivert") {
            Log.i("onOptionsItemSelected()", "Øving 1 - Trykket på Sivert")
        }else if(item.title == "Utne"){
            Log.i("onOptionsItemSelected()", "Øving 1 - Trykket på Utne")
        }
        return true
    }
}