# Chatime

![Chatime](https://raw.githubusercontent.com/zacw7/chatime/main/static/logo.png)

Chatime is an online chatting application designed to help users connect with new people that they may not have the chance to know using traditional chat apps.

Final project for CS5520 Mobile Application Development.

## Author

Wen, Zhi (wen.zhi@northeastern.edu)

## Features
### Chat

Connect with another random user and start chat.
![chat](https://raw.githubusercontent.com/zacw7/chatime/main/static/chat.gif)

### Drift Bottle

Write down your thoughts and share it with the the world.

![create_drift_bottle](https://raw.githubusercontent.com/zacw7/chatime/main/static/create_drift_bottle.gif)

Pick a drift bottle, read the message and start a conversation.

![pick_drift_bottle](https://raw.githubusercontent.com/zacw7/chatime/main/static/pick_drift_bottle.gif)

## Installation

1. Download the [APK]().
2. Connect to an android device.
3. Enable adb debugging on your device following the [instruction](https://developer.android.com/studio/command-line/adb#Enabling).
4. Use adb to install an APK on an emulator or connected device with the install command:
   ```
   adb install [path_to_apk]
   ```

## Build from Source

1. Download the source code.
   ```
   git clone https://github.com/zacw7/chatime.git
   ```
2. Setup a Firebase project following the [instructions](https://firebase.google.com/docs/android/setup#console)
3. Once the Firebase Android configuration file is downloaded, add it into the module (app-level) directory of this app.
4. Setup Firebase functions following the [instructions](https://firebase.google.com/docs/functions/get-started#set-up-node.js-and-the-firebase-cli). The code can be found at [chatime-functions](https://github.com/zacw7/chatime-functions).
5. Build and run.

## Libraries used

- [Butterknife](https://github.com/JakeWharton/butterknife) for view injection.
- [Retrofit](https://github.com/square/retrofit) for network code.
- [Glide](https://github.com/bumptech/glide) for image loading.
- [Timber](https://github.com/JakeWharton/timber) for logging.

## References

- [Android-Clean-Boilerplate](https://github.com/dmilicic/Android-Clean-Boilerplate)
- [Android Developers](https://developer.android.com/guide)
- [Firebase](https://firebase.google.com/docs)
- [Material Design](https://material.io/)
