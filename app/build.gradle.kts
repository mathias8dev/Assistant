plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    namespace = "com.mathias8dev.assistant"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mathias8dev.assistant"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val openAiApiKey = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
            rootDir
        ).getProperty("OPENAI_API_KEY")
        buildConfigField(
            type = "String",
            name = "OPENAI_API_KEY",
            value = openAiApiKey
        )
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}


dependencies {
    val lifecycleVersion  = "2.6.2"
    val lottieVersion = "6.1.0"

    // LottieAnimation
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")

    // Custom Material3 AlertDialog
    implementation("androidx.compose.material3:material3:1.1.2")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // State Management (collectAsStateWithLifecycle) and others
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    // Datastore
    implementation("androidx.datastore:datastore:1.1.0-alpha05")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit gson converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit http logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    // ComposeDestination
    implementation("io.github.raamcosta.compose-destinations:core:1.9.54")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.48.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    ksp("io.github.raamcosta.compose-destinations:ksp:1.9.54")
    kapt("com.google.dagger:hilt-compiler:2.48.1")

    // Default
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}


kapt {
    correctErrorTypes = true
}
