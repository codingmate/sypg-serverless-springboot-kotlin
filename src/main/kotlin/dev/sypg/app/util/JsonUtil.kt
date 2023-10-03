package dev.sypg.app.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.stereotype.Component

@Component
class JsonUtil {
    fun jsonToMap(json: String): Map<String, Any> {
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return Gson().fromJson(json, mapType)
    }
    fun mapToJson(map: Map<String, Any>): String {
        return Gson().toJson(map)
    }
    fun setToJson(set: Set<Any>): String {
        return Gson().toJson(set)
    }

    fun listToJson(list: List<Any>): String {
        return Gson().toJson(list)
    }
}