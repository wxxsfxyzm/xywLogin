// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion: String by extra
    extra["kotlinVersion"] = "1.6.10"
    repositories {
        mavenCentral()
        //  google仓库maven地址要放在google()的上边,不要更改顺序
        maven { url = uri("https://dl.google.com/dl/android/maven2/") }
        google()
        maven { url = uri("https://jitpack.io") }
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://maven.aliyun.com/nexus/content/repositories/releases/")
        }
    }
    dependencies {
        classpath(
            "com.android.tools.build:gradle:7.1.3"
        )
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        // google仓库maven地址要放在google()的上边,不要更改顺序
        maven { url = uri("https://dl.google.com/dl/android/maven2/") }
        google()
        mavenCentral()
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://jitpack.io") }
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://maven.aliyun.com/nexus/content/groups/public/")
        }
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://maven.aliyun.com/nexus/content/repositories/releases/")
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
