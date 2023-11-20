package com.example.rickandmortywiki.utils

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import android.Manifest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.rickandmortywiki.ui.MainActivity
import org.junit.BeforeClass
import org.junit.Rule


abstract class MainTest: TestContext {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE)

    companion object {
        @JvmStatic
        @BeforeClass
        fun clearData() {
                val packageName = InstrumentationRegistry.getInstrumentation().context.packageName
                InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("pm clear")
        }
    }
}