# File Downloader
###  A file downloader library for Android.



## Using Library in your Android application

Update your settings.gradle file with the following dependency.

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } // this one
    }
}
```

Update your module level build.gradle file with the following dependency.

```groovy
dependencies {
   implementation 'com.github.pavan7387:FileDownloader:1.0.0'
}
```
Do not forget to add internet permission in manifest if already not present

```
<uses-permission android:name="android.permission.INTERNET" />
```

