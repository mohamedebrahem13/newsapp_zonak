package com.example.newsapp_zonak

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EndToEndTest {

    private lateinit var uiDevice: UiDevice

    @Before
    fun setUp() {
        // Initialize UiDevice instance
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        uiDevice.pressHome()

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val packageName = "com.example.newsapp_zonak"
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous activities
        }
        context.startActivity(intent)
        uiDevice.waitForIdle() // Wait for the app to be idle
    }

    @Test
    fun testHomeScreenDisplaysCorrectly() {
        // Verify that the Home screen is displayed by checking for a specific category name
        val categoryItem: UiObject = uiDevice.findObject(
            UiSelector().text("business") // Change this if you have different categories
        )
        categoryItem.waitForExists(5000) // Wait for the category to appear
        assert(categoryItem.exists()) // Assert that the category exists
    }

    @Test
    fun testNewsListDisplaysCorrectly() {
        // Verify that the news list is displayed
        val newsList: UiObject = uiDevice.findObject(
            UiSelector().description("news_list") // This should match the contentDescription of your LazyColumn in HomeNewsScreen
        )
        newsList.waitForExists(5000)
        assert(newsList.exists()) // Assert that the news list is displayed
    }

    @Test
    fun testArticleNavigation() {
        // Select the first article in the news list using test tag or content description
        val articleItem: UiObject = uiDevice.findObject(
            UiSelector().descriptionContains("Image for article:") // Use the content description for the image in NewsItem
        )
        articleItem.click() // Click on the article image to navigate to the details screen

        // Wait for navigation and verify that the Article Details screen is displayed
        val articleDetails: UiObject = uiDevice.findObject(
            UiSelector().descriptionContains("Article Title") // Check for the article title in the details screen
        )
        articleDetails.waitForExists(5000)
        assert(articleDetails.exists()) // Assert that the article details screen exists
    }

    @Test
    fun testBackNavigation() {
        // Navigate to article details screen first
        testArticleNavigation()

        // Verify that the Back Button works
        val backButton: UiObject = uiDevice.findObject(
            UiSelector().description("Back Button") // Description used in TopAppBar for the back button
        )
        backButton.click() // Click on the back button to return to the Home screen

        // Verify that the Home screen is displayed again
        testHomeScreenDisplaysCorrectly()
    }
}