plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.polotika.routetask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.polotika.routetask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","BASE_URL","\"https://dummyjson.com/\"")

        }

        debug{
            buildConfigField("String","BASE_URL","\"https://dummyjson.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
    implementation("androidx.compose.material3:material3-android:1.2.0-rc01")
    testImplementation(libs.junit)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation( "androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt ("com.google.dagger:hilt-compiler:2.48")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    //ViewModel lifecycle
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation( "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation( "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    // For local unit tests
    testImplementation( "com.google.dagger:hilt-android-testing:2.39.1")
    kaptTest ("com.google.dagger:hilt-compiler:2.48")

    //Retrofit
    implementation( "com.squareup.retrofit2:retrofit:2.9.0")
    implementation( "com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.google.code.gson:gson:2.6.2")
    
    //Okhttp
    implementation( "com.squareup.okhttp3:logging-interceptor:4.9.0")


    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")
}