package com.example.rickandmortywiki.utils.actions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf

class ScrollRecyclerViewToElement(private val elementIndex: Int): ViewAction {
        override fun getDescription(): String {
            return "scroll RecyclerView to bottom"
        }

        override fun getConstraints(): Matcher<View> {
            return AllOf.allOf<View>(
                ViewMatchers.isAssignableFrom(RecyclerView::class.java),
                ViewMatchers.isDisplayed()
            )
        }
        override fun perform(uiController: UiController?, view: View?) {
            val recyclerView = view as RecyclerView
            val itemCount = recyclerView.adapter?.itemCount
            if(itemCount != null && elementIndex < itemCount) {
                val position = elementIndex
                recyclerView.scrollToPosition(position)
                uiController?.loopMainThreadUntilIdle()
            }
        }
}