plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") version "1.9.20-1.0.13"
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.abanapps.videoplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.abanapps.videoplayer"
        minSdk = 22
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.android.v251)
    implementation(libs.androidx.hilt.navigation)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation (libs.constraintlayout.compose)
    implementation (libs.androidx.media3.exoplayer)
    implementation (libs.androidx.media3.ui)
    implementation (libs.androidx.media3.common)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.video)
    implementation (libs.androidx.material.icons.extended)
    implementation("androidx.media3:media3-session:1.4.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")




}

ksp {
    arg("correctErrorTypes", "true")
}


