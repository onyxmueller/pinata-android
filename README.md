![header](https://docs.mypinata.cloud/ipfs/QmP9PGe3PdUqmsq8xY4sEW3qgdXx4WT9ictTWCb3qyzz3s?img-format=webp)<br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/onyxmueller/pinata-android/actions"><img alt="Build Status" src="https://github.com/onyxmueller/pinata-android/actions/workflows/build.yml/badge.svg"/></a>
</p>

The Pinata Android library provides convenient access to the Pinata API from your Android applications.

## Download
[![Maven Central](https://img.shields.io/maven-central/v/net.onyxmueller.pinata/pinata.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/net.onyxmueller.pinata/pinata)

### Gradle

Add the dependency below into your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation("net.onyxmueller.pinata:pinata:0.1.0")
}
```

## Documentation

### PinataApiResponse

`PinataApiResponse` serves as an interface designed to create consistent responses from the Pinata API. It offers convenient extensions to manage your payloads, encompassing both body data and exceptional scenarios. `PinataApiResponse` encompasses three distinct types: **Success**, **Error**, and **Exception**.

#### PinataApiResponse.Success

This represents a successful response from the Pinata API. You can create an instance of [PinataApiResponse.Success] by giving the generic type and data.

```kotlin
val apiResponse = PinataApiResponse.Success(data = myData)
val data = apiResponse.data
```

#### PinataApiResponse.Exception

This signals a failed tasks captured by unexpected exceptions during a Pinata API request creation or response processing on the client side, such as a network connection failure. You can obtain exception details from the `PinataApiResponse.Exception`.

```kotlin
val apiResponse = PinataApiResponse.Exception(exception = HttpTimeoutException())
val exception = apiResponse.exception
val message = apiResponse.message
```

#### PinataApiResponse.Error

This denotes a failed Pinata API request, typically due to bad requests or internal server errors. You can additionally put an error payload that can contain detailed error information.

```kotlin
val apiResponse = PinataApiResponse.Error(payload = errorBody)
val payload = apiResponse.payload
```

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/onyxmueller/pinata-android/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/onyxmueller)__ me for my next creations! 🤩

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
