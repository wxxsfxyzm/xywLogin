plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    // kotlin("android.extensions")
}
val ankoVersion by extra("0.10.8")
val roomVersion by extra("2.4.1")

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.carlyu.logindemo"
        minSdk = 29
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //指定room.schemaLocation生成的文件路径
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
            }

            compileOptions {
                sourceCompatibility("11")
                targetCompatibility("11")
            }

            kotlinOptions {
                jvmTarget = "11"
            }
            buildToolsVersion = "31.0.0"
            buildFeatures {
                viewBinding = true
            }

        }

        dependencies {
            //implementation("javax.annotation.processing:1.3.2")
            implementation("com.dylanc:viewbinding-ktx:1.0.0")
            implementation("org.jetbrains.anko:anko-commons:$ankoVersion")
            implementation("androidx.core:core-ktx:1.6.0")
            implementation("androidx.appcompat:appcompat:1.4.0")
            implementation("androidx.room:room-runtime:$roomVersion")
            implementation("androidx.room:room-common:$roomVersion")
            implementation("androidx.room:room-ktx:$roomVersion")
            kapt("androidx.room:room-compiler:$roomVersion")
            implementation("com.google.android.material:material:1.4.0")
            implementation("androidx.constraintlayout:constraintlayout:2.1.2")
            implementation("com.squareup.retrofit2:retrofit:2.9.0")
            implementation("com.squareup.retrofit2:converter-gson:2.9.0")
            implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.4")
            implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.4")
            implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
            testImplementation("junit:junit:4.13.2")
            androidTestImplementation("androidx.test.ext:junit:1.1.3")
            androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}