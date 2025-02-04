./gradlew assembleAppSharedLogicMultiPlatformXCFramework --info
rm -rf ./builds/iosLib/*
cp -R ./shared/build/XCFrameworks/* ./builds/iosLib/