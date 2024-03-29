plugins {
    id("com.android.application")
}

android {
    namespace = "pe.edu.idat.appborabora"
    compileSdk = 34

    defaultConfig {
        applicationId = "pe.edu.idat.appborabora"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("androidx.room:room-common:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.8.6")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    //Carrusel
    implementation("com.github.smarteist:autoimageslider:1.4.0")
    // Google Play services - Authentication
    implementation ("com.google.android.gms:play-services-auth:19.2.0")
    //Implementaciones de view model
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //Implementación para convertir objetos JSON
    implementation("com.google.code.gson:gson:2.8.9")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //ROOM
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")
    //Sweet Alert
    implementation ("com.github.f0ris.sweetalert:library:1.6.2")
    //Slider
    implementation ("com.github.smarteist:autoimageslider:1.4.0")
}

