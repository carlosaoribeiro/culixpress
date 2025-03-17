plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.carlosribeiro.culixpress"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.carlosribeiro.culixpress"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Lifecycle components (ViewModel, LiveData, Extensions)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

// Retrofit (Networking & JSON parsing)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

// Glide (Image Loading)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

// RecyclerView and CardView (UI components)
    implementation(libs.recyclerview)
    implementation(libs.cardview)

// Room (Database)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation (libs.recyclerview.v121)

    implementation (libs.picasso)
}