# cspell:ignore sdkman sdkmanager promp commandlinetools Jenkinsfile cmdline

# java stuff
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
# install and pas yes for setting it as active in the interactive promp
yes | sdk install java 17.0.13-zulu
# if already installed it will not be set active
# so also use the use command to make it active
sdk use java 17.0.13-zulu
## make it default for all shell instances
#sdk default java 17.0.7-zulu

#android stuff
brew install --cask android-commandlinetools
# ANDROID_HOME ==> `brew --prefix`/share/android-commandlinetools
# inside Jenkinsfile androidHome
yes | sdkmanager --licenses
sdkmanager "build-tools;35.0.0" "platforms;android-35" "emulator" "tools" "platform-tools"

bundle install