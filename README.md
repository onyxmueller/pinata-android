![header](assets/images/pinata_for_android.jpg)<br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/onyxmueller/pinata-android/actions"><img alt="Build Status" src="https://github.com/onyxmueller/pinata-android/actions/workflows/build.yml/badge.svg"/></a>
</p>

[Pinata is the Internet's File API](https://pinata.cloud/), allowing you to upload, manage, and deliver your files effortlessly—no drama, no compromises. With Pinata Android library, you can now bring Pinata’s powerful file-handling capabilities directly into your Android/Kotlin apps quickly and easily.

## Download
[![Maven Central](https://img.shields.io/maven-central/v/net.onyxmueller.pinata/pinata.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/net.onyxmueller.pinata/pinata)

### Gradle

Add the dependency below into your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation("net.onyxmueller.pinata:pinata:0.1.3")
}
```

## Documentation

## Usage

To access the API, you'll need to pass your [Pinata API Key JWT](https://docs.pinata.cloud/account-management/api-keys) and [Dedicated Gateway Domain](https://docs.pinata.cloud/gateways/retrieving-files). More details at https://docs.pinata.cloud/quickstart.

```kotlin
        val pinataClient = PinataClient.get(PINATA_JWT_TOKEN, PINATA_GATEWAY)
```

### Files API

```kotlin
// Upload a file
val filesDir = applicationContext.filesDir
val file = File(filesDir, "image.jpg")
val uploadResult = pinataClient.files.upload(file)

// List all files
val listResult = pinataClient.files.list()

// Filter on files
val filteredListResult = pinataClient.files.list(name = "image.jpg")

// Get a specific file
val fileResult = pinataClient.files.get("1234567890")

// Create signed URL
val signResult = pinataClient.files.sign("1234567890", 604800) // expires in a week

// Update a file
val updateResult = pinataClient.files.update("1234567890", "new_name.jpg", mapOf("location" to "Earth"))

// Delete a file
val deleteResult = pinataClient.files.delete("1234567890")
```

### PinataApiResponse

`PinataApiResponse` serves as an interface designed to create consistent responses from the Pinata API. It offers convenient extensions to manage your payloads, encompassing both body data and exceptional scenarios. `PinataApiResponse` encompasses three distinct types: **Success**, **Error**, and **Exception**.

#### PinataApiResponse Extensions

You can effectively handle PinataApiResponse using the following extensions:

- onSuccess: Executes when the PinataApiResponse is of type ApiResponse.Success. Within this scope, you can directly access the body `data`.
- onError: Executes when the PinataApiResponse is of type PinataApiResponse.Error. Here, you can access the error `code` and `message` here.
- onException: Executes when the ApiResponse is of type PinataApiResponse.Exception. You can access the exception here.

Each scope operates according to its corresponding ApiResponse type:

```kotlin
val response = pinataClient.files.list()
response.onSuccess {
    // this scope will be executed if the request successful.
    // handle the success case
  }.onError {
    // this scope will be executed when the request failed with errors.
    // handle the error case
  }.onException {
   // this scope will be executed when the request failed with exceptions.
   // handle the exception case
  }
```

## Find this library useful? :raised_hands:
Support it by joining __[stargazers](https://github.com/onyxmueller/pinata-android/stargazers)__ for this repository. :star: And __[follow me](https://github.com/onyxmueller)__ for other creations.

## Shoutouts :loudspeaker:

- A big shoutout goes out to Erik ([@eriklubbers](https://github.com/ErikLubbers)) for reviewing and providing feedback.

# License
```xml
Copyright 2024 onyxmueller (Onyx Mueller)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
