buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.8")
        classpath("com.google.firebase:perf-plugin:1.4.2")
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
}
