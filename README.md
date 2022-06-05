# Recipe Planner

## Outline

[Team Members](#team-members)

[Featues](#features)

[Architecture](#architecture)

[Project Structure](#project-structure)

[Testing](#testing)

[Changelog](#changelog)

[Development Log](#development-log)

## Team Members

- Anthony Phung (@tormin4)
- Daniel La Rocque (@dlarocque)
- Emerson Lesage-Dong (@emersonlesage)
- Izan Cuetara Diez (@Unstavle)
- Samuel Barrett (@samuelbarrett)

## Features

## Architecture

## Project Structure

## Testing

This project uses GitHub actions to automate testing on all pull requests and all pushes to `develop` and `master` branches.

#### Environment

##### Unit Tests

- Ubuntu latest
- Script: `./gradlew test`

##### Instrumentation Tests

- MacOS latest
- JDK 11
- Android Emulator: [Android Emulator Runner](https://github.com/ReactiveCircus/android-emulator-runner)
- Script: `./gradlew connectedAndroidTest`

## Changelog

Changelog (CHANGELOG.md) is located at `/docs/CHANGELOG.md` ([GitHub](https://github.com/dlarocque/recipe-planner/blob/iteration1/docs/CHANGELOG.md]))

## Development Log

