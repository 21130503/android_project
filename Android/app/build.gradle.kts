plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.appbanhang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appbanhang"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    glider
//    androidTestImplementation "com.github.bumptech.glide:glide:4.16.0"
    implementation("com.github.bumptech.glide:glide:4.16.0")
//    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.40.5")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.40.5")

// RxJava 3
    implementation("io.reactivex.rxjava3:rxjava:3.1.3")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

// Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0") // For RxJava3 support)

// Room for local database
    implementation("androidx.room:room-runtime:2.4.1")
    annotationProcessor("androidx.room:room-compiler:2.4.1")
// Optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.4.1")

// LiveData and ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.4.0")
// Optional - Use LiveData with Kotlin Coroutines
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")

//    Notification
    implementation ("com.nex3z:notification-badge:1.0.4")
//    Green ROBOT
    implementation("org.greenrobot:eventbus:3.3.1")

    //Paper- Lưu thông tin người dùng khi thoát app
    implementation ("io.github.pilgr:paperdb:2.7.2")

    //animation
    implementation ("com.airbnb.android:lottie:6.4.1")

//
    implementation ("com.github.fornewid:neumorphism:0.3.0")


}

