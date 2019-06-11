package com.param.tinynews.test

import android.view.View
import androidx.test.rule.ActivityTestRule
import com.param.tinynews.MainActivity
import com.param.tinynews.R
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class MainActivityTest {
    @Rule @JvmField
    public var mainActivityTestRule:ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(MainActivity::class.java)
    private var mActivty: MainActivity? = null

    @Before
    fun setUp() {
        mActivty = mainActivityTestRule.activity
    }

    @Test
    fun testActivityLaunched(){
        var view: View? = mActivty?.findViewById(R.id.drawer_layout)
        assertNotNull(view)
    }
    @After
    fun tearDown() {
        mActivty = null
    }

    @Test
    fun onCreate() {


    }

    @Test
    fun onStart() {

    }

    @Test
    fun onRestart() {
    }

    @Test
    fun onResume() {
    }

    @Test
    fun onPause() {
    }

    @Test
    fun onStop() {
    }

    @Test
    fun onDestroy() {
    }

    @Test
    fun onSaveInstanceState() {
    }

    @Test
    fun onConfigurationChanged() {
    }

    @Test
    fun getOnClickListener() {
    }
}