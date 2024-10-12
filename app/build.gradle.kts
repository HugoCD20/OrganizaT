plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Asegúrate de incluir el plugin de Kotlin Kapt
    id("kotlin-kapt")
}

android {
    namespace = "com.example.organizat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.organizat"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    val version = "2.6.1"
    implementation("androidx.room:room-runtime:$version")
    kapt("androidx.room:room-compiler:$version") // Para Kotlin
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("androidx.fragment:fragment-ktx:1.5.0")
    implementation ("org.mindrot:jbcrypt:0.4")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}