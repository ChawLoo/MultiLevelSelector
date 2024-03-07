plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
    signing
}


val androidSourcesJar = task<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
    exclude("**/R.class")
    exclude("**/BuildConfig.class")
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin") {
            groupId = "io.github.chawloo"
            artifactId = "MultiLevelSelector"
            version = "1.1.1"
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
            artifact(androidSourcesJar)
            pom {
                name.set("MultiLevelSelector")
                description.set("A multi-level Selector for Android Application")
                url.set("https://github.com/ChawLoo/MultiLevelSelector")
                inceptionYear.set("2022")
                scm {
                    url.set("https://github.com/ChawLoo/MultiLevelSelector")
                    connection.set("scm:git:https://github.com/ChawLoo/MultiLevelSelector.git")
                    developerConnection.set("scm:git:https://github.com/ChawLoo/MultiLevelSelector.git")
                }
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                        comments.set("A business-friendly OSS license")
                    }
                }
                developers {
                    developer {
                        id.set("ChawLoo")
                        name.set("ChawLoo")
                        email.set("ChawLoo0827@qq.com")
                        url.set("https://github.com/ChawLoo/MultiLevelSelector")
                    }
                }
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/ChawLoo/MultiLevelSelector")
                }
                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    project.configurations.all {
                        val name = this.name
                        if (name != "implementation" && name != "compile" && name != "api") {
                            return@all
                        }
                        println(this)
                        dependencies.forEach {
                            println(it)
                            if (it.name == "unspecified") {
                                // 忽略无法识别的
                                return@forEach
                            }
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", it.group)
                            dependencyNode.appendNode("artifactId", it.name)
                            dependencyNode.appendNode("version", it.version)
                            if (name == "api" || name == "compile") {
                                dependencyNode.appendNode("scope", "compile")
                            } else { // implementation
                                dependencyNode.appendNode("scope", "runtime")
                            }
                        }
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releaseRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) {
                snapshotRepoUrl
            } else {
                releaseRepoUrl
            }
            credentials {
                username = rootProject.properties["maven.username"].toString()
                val pwd = rootProject.properties["maven.password"].toString()
                password = pwd
            }
        }
    }
}

signing {
    sign(publishing.publications)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        viewBinding = true
    }
    namespace = "cn.chawloo.multilevelselector"
}

dependencies {
    api(libs.bundles.androidx)
    api(libs.material)
    api(libs.brv)
}