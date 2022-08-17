# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keeping our own sdk
-keep interface vuukle.sdk.ads.callback.*
-keep class vuukle.sdk.ads.constants.*
-keep class vuukle.sdk.ads.exception.*
-keep class vuukle.sdk.ads.helper.*
-keep class vuukle.sdk.ads.manager.*
-keep interface vuukle.sdk.ads.manager.*
-keep class vuukle.sdk.ads.model.*
-keep class vuukle.sdk.ads.prebid.*
-keep class vuukle.sdk.ads.provider.*

# Keeping prebid sdk
-keep public class org.prebid.mobile.*
-keep interface org.prebid.mobile.*
-keep enum org.prebid.mobile.*

-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile