package com.tony.demo.kotlintest

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * description: prefs 代理 相关
 *
 * @author tony
 */
class ByPreference<T>(val context: Context, val name: String, val default: T) : ReadWriteProperty<Any?, T> {

    val prefs: SharedPreferences by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("超过获取类型范围")
        }
        return res as T
    }

    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("超过设置类型范围")
        }.apply()
    }
}