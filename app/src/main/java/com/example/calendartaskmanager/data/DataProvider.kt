package com.example.calendartaskmanager.data

import android.content.Context

abstract class DataProvider<T> (
    val context: Context
) {
    abstract fun loadData(data: Any? = null): T
    abstract fun saveData(data: T, context: Context)
    abstract fun getById(id: Long): Any
}