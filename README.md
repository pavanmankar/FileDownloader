# File Downloader
###  A file downloader library for Android with pause and resume support.


### Overview of library
* This library can be used to download any type of file like images, video, pdf, APK etc.
* Supports large file download.
* Supports proper request canceling.
* We can check the status of downloading with the given download ID.
* Many requests can be made in parallel.
* This file downloader library supports pause and resume while downloading a file.
* This downloader library has a simple interface to make download requests.
* This library gives callbacks for everything like onProgress, onCancel, onStart, onError, etc while downloading a file.
* All types of customization are possible.



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
   implementation 'com.github.pavanmankar:FileDownloader:1.0.0'
}
```
Do not forget to add internet permission in manifest if already not present

```
<uses-permission android:name="android.permission.INTERNET" />
```

### Download Request 
```
val request = downloader.newReqBuilder(
                    url,
                    dirPath,
                    fileName,
                ).tag(TAG).build()

downloadId = downloader.enqueue(request, 
    onStart = {
    },    
    onProgress = {
    }, 
    onPause = {
    }, 
    onCompleted = {
    }, 
    onError = {
    }
)
```

### Pause a download request :

```kotlin
downloader.pause(downloadId);
```
### Resume a download request
```kotlin
downloader.resume(downloadId);
```

### Cancel a download request
```kotlin
// Cancel with the download id
downloader.cancel(downloadId);

//Cancel by using tag
downloader.cancel(TAG);

// Cancel all the requests
downloader.cancelAll();
```

### License
```
   Copyright (C) 2024 Pavan Mankar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
   
