![Compose QR](logo.svg)

<p align="start">
  <a href="https://central.sonatype.com/artifact/run.nabla/qr"><img alt="maven" src="https://img.shields.io/maven-central/v/run.nabla/qr"/></a>
  <a href="https://github.com/nabla-run/Compose-QR"><img alt="kotlin" src="https://img.shields.io/badge/Kotlin-100%25-brightgreen"/></a>
  <a href="https://developer.android.com/about/versions/nougat/android-7.0"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/nabla-run/Compose-QR/blob/main/LICENSE"><img alt="license" src="https://img.shields.io/github/license/nabla-run/Compose-QR"/></a>
</p>

The QRImage component allows you to easily integrate a QR code into your Compose UI.

## Setup

Library is available on `mavenCentral()`.

```kotlin
repositories {
  mavenCentral()
}
```

```kotlin
implementation("run.nabla:qr:1.0.0")
```

## Screenshots

<img src="images/screenshot.png" alt="Screenshot showing qr" height="450px"/>

## Example

Call the QRImage component just like any other Compose component:

```kotlin
QRImage(
    type = QRType.ROUND,
    text = "demo",
    imageSize = Size(700f, 700f)
)
```

## Contributing

Contributions to Compose-QR are welcome! If you find any issues or have suggestions for
improvements, please create an issue or submit a pull request.

## License

    Copyright 2023 Nable Contributors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.