# MultiLevelSelector

### 介绍

MultiLevelSelector是一个针对多级别的选择封装的选择器，它是一个纯粹的选择器，属于自定义View故，可以放在Activity/Fragemnt的布局中的任意位置，也可以放在PopupWindow中作为一个弹窗选择器。

### 环境依赖

因需要用到列表，故本身依赖RecyclerView和BaseRecyclerViewAdapterHelper适配器框架。

```groovy
//项目根目录build.gradle
allprojects {
  repositories {
        google()
        mavenCentral()
    }
}
// Project dependencies
dependencies {
    implementation '**'
}
```

```kotlin
//项目根目录build.gradle.kts
allprojects {
  repositories {
        google()
        mavenCentral()
    }
}
// Project build.gradle.kts  dependencies
dependencies {
    implementation("**")
}
```




### 更新日志

**【Release】1.0** (2021年10月28日)
	第一版上线了，目前应用于自家项目，也经过测试，欢迎引用

### 例子预览

### 打赏（您的支持是我持续更新的动力）
![image](https://user-images.githubusercontent.com/26214519/139214994-71b782c2-7a42-4e66-8819-364fdb76e420.png)
