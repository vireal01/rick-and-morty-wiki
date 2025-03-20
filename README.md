<h2> UI and Unit Tests </h2>
There are 2 kind of UI tests: 

- Espresso (TODO: Rewrite the tests to Jetpack Compose testing framework in the `compose_layouts` branch)
- Appium

 The `Espresso` tests are located in `androidTest` dirrectory. And for the `compose_layouts` they are kinda useless,
 because all the screens are build with compose.

 To run Espresso tests use `./gradlew test connectedAndroidTest`.

 The `Appium` tests are loacated in the `test [unitTest]` dirrectory. 

 To run Appium tests (and Unit tests also) use `./gradlew testDebugUnitTest`.

 The tests itself are kind useless, but it has an implementation of the Page Object pattern and can help you to understand, how to setup ui testing from scratch.

 <h3>How to prepare to the Appium tests run:</h3>
 
 1. Setup Appium using the [Instruction](https://www.browserstack.com/guide/download-and-install-appium).
 2. Start Appium setver usingn `appium server --address localhost --port 4723 --use-drivers uiautomator2 --base-path /wd/hub`.
 3. Run the tests.
 
