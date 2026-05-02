plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.qrakon"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.qrakon"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    // ✅ Compatible with Kotlin 1.9.24
    composeOptions {
        kotlinCompilerExtensionVersion = "1.9.24"
//        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // 🔹 Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // 🔹 Compose BOM (controls all Compose versions)
    implementation(platform(libs.androidx.compose.bom))

    // 🔹 Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // 🔹 Foundation & Layout
    implementation("androidx.compose.foundation:foundation")

    // 🔹 Material Design
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // 🔹 Navigation
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // 🔹 Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // 🔹 Optional (keep if you need them)
    implementation(libs.androidx.camera.core)
    implementation(libs.play.services.wallet)
    implementation(libs.litert.support.api)

    // 🔹 Debug tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // 🔹 Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    androidTestImplementation("androidx.concurrent:concurrent-futures:1.2.0")
    androidTestImplementation("androidx.concurrent:concurrent-futures-ktx:1.2.0")
}

configurations.all {
    resolutionStrategy {
        force("androidx.concurrent:concurrent-futures:1.2.0")
        force("androidx.concurrent:concurrent-futures-ktx:1.2.0")
    }
}