# README #

This is a sample project shyowcasing a crash when trying to use a JsonObject.

## How to reproduce ##

Open the Crash.xcodeproj.  
Enable breakpoints.  
Run the application.  

It will now crash when trying to print the JsonObject.  

`Could not cast value of type 'ASLMPKMapAsNSDictionary' (0x1040305f0) to 'ASLMPKotlinx_serialization_jsonJsonElement' (0x104030198).`

## Multiplatform project ##

In the appsharedlogic folder you can find the Kotlin Multiplatform project.  
Binraries have been build for the iOS and tvOS simulator and android.  
In order to build the libraries youself you can execute `./scripts/buildAllLibs.sh` from inside the appsharedlogic folder aka the root of the multiplatform project.

## Additional details ##

The Alternative.kt file has a variable called QueryDefinition which is a JsonObject.  
Reason for that is that the structure of this is unknown to the clients and will need to be send to the server.  
This object can be configured server side to determine what should be shown in the client.  
And it needs to be send to the server as a JsonObject.  
On Android mobile and Android TV this works as expected.  
But on tvOS it crashes when you try to access this property.  