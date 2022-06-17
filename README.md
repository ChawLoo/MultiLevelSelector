# MultiLevelSelector
<a href ="https://search.maven.org/artifact/io.github.chawloo/MultiLevelSelector">
<img src="https://img.shields.io/maven-central/v/io.github.chawloo/MultiLevelSelector"/>
</a>

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
   implementation 'io.github.chawloo:MultiLevelSelector:1.0.2'
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
   implementation("io.github.chawloo:MultiLevelSelector:1.0.2")
}
```



### 选择器使用说明

选择器支持目前支持两种模式

- TYPE_ONLY_ONE_LIST_MODE = 0//同一级列表

  所有N级列表均为同一级列表，例如：

``` json
[
    {
        "id":1,
        "lastId":-1
    },
    {
        "id":12,
        "lastId":1
    }
]
  ```

- TYPE_CHILDREN_NEXT_MODE = 1//包含下一级列表模式

``` json
[
    {
        "id":1,
        "lastId":-1,
        "child":[
            {
                "id":12,
                "lastId":1,
                "child":[
                    {
                        "id":121,
                        "lastId":12
                    }
                ]
            },
            {
                "id":13,
                "lastId":1
            }
        ]
    }
]
```
默认初始一级的lastId为-1，如需增加新的，可提Issues来增加需求


### 更新日志

**【Release】1.0.2** (2021年11月17日)
1.0.2以前的被吃了，因为创建了仓库，没有上传AAR和依赖  QAQ
第一版上线了，目前应用于自家项目，也经过测试，欢迎引用,

**【Release】1.0.3** (2022年6月17日)
1.更新了IMultiLevelEntity接口中的属性改为val
2.升级Gradle
### 例子预览

1. 整体预览
   ![image](https://github.com/ChawLoo/MultiLevelSelector/blob/master/screenshot/%E6%95%B4%E4%BD%93%E6%BC%94%E7%A4%BA.gif)
2. 单List截图
   ![image](https://github.com/ChawLoo/MultiLevelSelector/blob/master/screenshot/%E4%B8%80%E4%B8%AA%E5%88%97%E8%A1%A8%E5%A4%9A%E7%BA%A7%E9%80%89%E6%8B%A9.png)
3. 多级列表
   ![image](https://github.com/ChawLoo/MultiLevelSelector/blob/master/screenshot/%E7%9C%81%E5%B8%82%E5%8C%BA%E5%AD%90%E9%9B%86%E6%A8%A1%E5%BC%8F.png)

### 打赏（您的支持是我持续更新的动力）

![image](https://user-images.githubusercontent.com/26214519/139214994-71b782c2-7a42-4e66-8819-364fdb76e420.png)

