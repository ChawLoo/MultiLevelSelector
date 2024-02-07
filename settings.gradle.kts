pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://packages.aliyun.com/maven/repository/2418478-release-GhmPUt/")
            credentials {
                username = "609399173a10edbf367f5264"
                password = "=RTs0bvMruGT"
            }
        }
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        flatDir {
            dirs("libs")
        }
    }

    /**
     * 别名必须由一系列标识符组成，由破折号 ( -, 推荐)、下划线 ( _) 或点 ( .) 分隔。标识符本身必须由 ascii 字符组成，最好是小写，最后是数字。
     * guava是一个有效的别名
     * groovy-core是一个有效的别名
     * commons-lang3是一个有效的别名
     * androidx.awesome.lib也是一个有效的别名
     */
    versionCatalogs {
        create("libs") {
            from("io.github.chawloo:VersionControlPlugin:1.5.1-RC4")
        }
    }
}

rootProject.name = "MultiLevelSelector"
include(":example")
include(":MultiLevelSelector")
