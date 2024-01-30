package com.example.rickandmortywiki.appium_tests

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.remote.MobilePlatform
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

@Suppress("VariableNaming")
open class AppiumBaseTest(val automationName: String = AutomationName.ANDROID_UIAUTOMATOR2) {

    lateinit var driver: AndroidDriver

    private val DEFAULT_APPIUM_ADDRESS = "http://0.0.0.0:4723/wd/hub"

    fun makeDriver() {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability(
            "appium:${MobileCapabilityType.DEVICE_NAME}",
            "Pixel 2 API 34"
        )
        capabilities.setCapability(
            "appium:${AndroidMobileCapabilityType.APP_PACKAGE}",
            "com.example.rickandmortywiki"
        )
        capabilities.setCapability(
            "appium:${AndroidMobileCapabilityType.APP_ACTIVITY}",
            ".ui.MainActivity"
        )
        capabilities.setCapability(
            "appium:${MobileCapabilityType.PLATFORM_NAME}",
            MobilePlatform.ANDROID
        )
        capabilities.setCapability(
            "appium:${MobileCapabilityType.AUTOMATION_NAME}",
            automationName
        )

        driver = AndroidDriver(URL(DEFAULT_APPIUM_ADDRESS), capabilities)
    }

    fun tearDown() {
        driver.quit()
    }
}
