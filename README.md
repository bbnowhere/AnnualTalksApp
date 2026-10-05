# Annual Talk 2018–2020 Android App

An Android event companion app whose checked-in application content is branded **NCBS Annual Talks 2020**. The app presents the four-day event schedule and provides links to live event information, poster listings, campus guidance, and event contacts.

## Background

The repository name and project title refer to Annual Talk 2018–2020. The application resources and schedule pages in this repository specifically identify the **NCBS Annual Talks 2020** event, with schedules dated 14–17 January 2020. The checked-in source and assets do not establish what app content, if any, was provided for 2018 or 2019.

## App features

- **Schedule:** Four date tabs (14, 15, 16, and 17 January) display event schedule pages packaged under `app/src/main/assets`. Some schedule links lead to event pages hosted online.
- **Live:** Opens the NCBS Annual Talks 2020 website in an in-app `WebView`.
- **Posters:** Shows poster evaluation dates, the full poster listing, and a poster floor layout link. The listing is packaged locally; its linked poster content is online.
- **Useful links:** Provides packaged shuttle information and a campus map, plus an online feedback link and telephone links for event contacts.
- **Connectivity handling:** Checks network connectivity before opening schedule tabs and navigation items, and displays an offline message when disconnected. Online pages require a network connection.

## Screenshots

No app screenshots are included in the repository. The resources do include app artwork and a campus map, but these are not screenshots of the running app.

## Technology stack

| Component | Version or details |
| --- | --- |
| Application language | Java; no Kotlin source files are present |
| Android Gradle Plugin | 4.0.1 |
| Gradle wrapper | 6.1.1 (`all` distribution) |
| Compile SDK | 28 |
| Minimum SDK | 15 |
| Target SDK | 28 |
| Application ID | `in.res.ncbs.at` |
| Configured app version | Version code `3`, version name `1.3` |
| Android support libraries | AppCompat 28.0.0-alpha1; Design Support 28.0.0-alpha1 |
| ConstraintLayout | 1.1.3 |
| Web content styling | Bundled Bootstrap CSS 3.4.1; the assets also contain Bootstrap JavaScript 4.0.0-alpha.6 |

The root build file declares the Kotlin Gradle plugin 1.3.61 on the buildscript classpath, but the app module does not apply that plugin and the repository contains no Kotlin source. The app uses the older Android Support Library packages (`android.support.*`), rather than AndroidX.

The module does not set an explicit Android Build Tools version; Gradle/Android Gradle Plugin selects it from the installed SDK. Java source and target compatibility are not explicitly set in the build files.

### Other declared dependencies

- JUnit 4.12 for local tests
- Android Test Runner 1.0.2 and Espresso Core 3.0.2 for instrumentation tests

These test libraries are declared in Gradle; this README does not imply that a test suite is present or has been run.

## Repository layout

```text
.
├── app/
│   ├── build.gradle                 # Android application module configuration
│   ├── release/                     # Checked-in APK and AAB artifacts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── assets/                  # Schedule and event HTML, CSS, JS, and map
│       ├── java/in/res/ncbs/at/     # Java activities and schedule fragments
│       └── res/                     # Layouts, menus, drawables, and values
├── build.gradle                     # Android Gradle Plugin and repositories
├── gradle/wrapper/                  # Gradle wrapper configuration and JAR
├── gradle.properties
└── settings.gradle                  # Includes the :app module
```

## Requirements

- JDK compatible with Gradle 6.1.1 and Android Gradle Plugin 4.0.1. Java 8 is a suitable baseline for this project generation; Java compatibility is not pinned in the repository.
- Android SDK Platform 28, specified by `compileSdkVersion`.
- Android SDK Platform 15 or later for the configured minimum API level.
- Network access for Gradle to resolve dependencies from the configured Google and JCenter repositories, unless the required artifacts are already cached.

This is a historical Android project using Gradle 6.1.1, Android Gradle Plugin 4.0.1, and Android Support Libraries. Newer Android Studio or JDK installations may need a compatible Gradle/JDK setup; the repository configuration alone does not establish that it fails with any particular current installation.

## Development setup

1. Clone or download the repository.
2. Open the project root in Android Studio, or use the included Gradle wrapper from a terminal.
3. Install Android SDK Platform 28 when prompted by Android Studio or the SDK Manager.
4. Select a JDK supported by Gradle 6.1.1 (Java 8 is a suitable baseline for this project generation).
5. Allow Gradle to sync and download the declared dependencies.

The app module is `app`. The wrapper distribution is configured in [`gradle/wrapper/gradle-wrapper.properties`](gradle/wrapper/gradle-wrapper.properties); the Android plugin version and app SDK values are in [`build.gradle`](build.gradle) and [`app/build.gradle`](app/build.gradle).

## Build

From the repository root, build a debug APK with the wrapper:

```sh
./gradlew assembleDebug
```

On Windows, run:

```bat
gradlew.bat assembleDebug
```

The Gradle `assembleDebug` task writes the APK to `app/build/outputs/apk/debug/app-debug.apk`.

## Install and run

You can run the app on an emulator or connected device from Android Studio by selecting the `app` configuration and choosing **Run**. The manifest configures `SplashActivity` as the launcher activity.

To install the debug APK built above on a connected device with Android Debug Bridge (ADB):

```sh
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

The repository also contains prebuilt artifacts in [`app/release/`](app/release/), including `app-release.apk` and `1.2.apk`. Their presence does not establish which source revision or signing setup produced them. Building from source with `assembleDebug` is the reproducible path documented here.

## Known limitations and historical notes

- The included event material is dated January 2020. Online links point to the NCBS Annual Talks 2020 site and may no longer be available or current.
- The app uses `WebView` for schedule pages and online content. Schedule navigation performs a connectivity check even though the date HTML files are packaged locally.
- The project targets API 28 and uses Android Support Library 28.0.0-alpha1. Gradle, plugin, and dependencies are documented as found; they have not been upgraded.
- There is no explicit `buildToolsVersion` or Java source compatibility declaration, and the repository contains no Kotlin source files.

## License

No repository-level license file is present, so no project license is stated here.
