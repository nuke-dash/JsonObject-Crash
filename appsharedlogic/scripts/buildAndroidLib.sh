./gradlew publishAndroidReleasePublicationToLocalTestRepoRepository --info
rm -rf ./builds/com
cp -R ./shared/build/local-repository/* ./builds/