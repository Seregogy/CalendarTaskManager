package com.example.calendartaskmanager.data

import android.content.Context

abstract class DataProvider<T> {
    abstract fun loadData(data: Any? = null): T
    abstract fun saveData(data: T, context: Context): Unit
}