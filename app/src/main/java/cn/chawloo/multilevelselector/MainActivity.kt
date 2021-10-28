package cn.chawloo.multilevelselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.chawloo.multilevelselector.widget.MultiLevelSelector

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val dataList = ArrayList<TestEntity>()
        for (i in 1..3) {
            dataList.add(TestEntity(categoryId = i.toLong(), parentId = -1, categoryName = "1-${i}").apply {
                isNew = i % 3 == 0
            })
        }
        for (i in 1..3) {
            for (p in 10..20) {
                dataList.add(TestEntity(categoryId = p.toLong(), parentId = i.toLong(), categoryName = "2-${i}-${p}").apply {
                    isNew = p % 3 == 0
                })
            }
        }
        for (i in 10..20) {
            for (p in 100..110) {
                dataList.add(TestEntity(categoryId = p.toLong(), parentId = i.toLong(), categoryName = "2-${i}-${p}").apply {
                    isNew = p % 3 == 0
                })
            }
        }
        for (i in 100..110) {
            for (p in 1000..1010) {
                dataList.add(TestEntity(categoryId = p.toLong(), parentId = i.toLong(), categoryName = "2-${i}-${p}").apply {
                    isNew = p % 3 == 0
                })
            }
        }
        val multiLevelSelector: MultiLevelSelector<TestEntity> = findViewById(R.id.multiLevel)
        multiLevelSelector.setData(dataList) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        multiLevelSelector.checkRepeat = {
            false
        }
    }
}