package com.example.rickandmortywiki.utils

import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers


private const val DEFAULT_VERIFY_TIMEOUT_MS = 10000
private var defaultVerificationTimeout = DEFAULT_VERIFY_TIMEOUT_MS


fun ViewInteraction.checkDisplayed(): ViewInteraction {
    return check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.isDisplayed())))
}

fun ViewInteraction.click(): ViewInteraction {
    return checkDisplayed().perform(ViewActions.click())
}

fun ViewInteraction.waitUntilAppears(actions: ViewAction? = null, timeout: Long = 5000, checkInterval: Long = 100): ViewInteraction {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeout

    do {
        try {
            return checkDisplayed()
        } catch (_: Exception) {
            actions
            if (System.currentTimeMillis() > endTime) {
                println()
            }
        }
        Thread.sleep(checkInterval)
    } while (System.currentTimeMillis() < endTime)
    return checkDisplayed()
}
