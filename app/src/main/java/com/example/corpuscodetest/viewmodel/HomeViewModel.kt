package com.example.corpuscodetest.viewmodel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpuscodetest.model.HomeItem
import com.example.corpuscodetest.model.HomeSection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class HomeViewModel  : ViewModel() {
    private val _items = MutableLiveData<List<HomeSection>>()
    val items: LiveData<List<HomeSection>> = _items

    fun loadData(assetManager: AssetManager) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = assetManager.open("carousal.json").bufferedReader().use { it.readText() }
            val parsed = parseJson(json)
            withContext(Dispatchers.Main) {
                _items.value = parsed
            }
        }
    }

    private fun parseJson(json: String): List<HomeSection> {
        val root = JSONObject(json)
        val array = root.getJSONArray("content")
        val list = mutableListOf<HomeSection>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val title = obj.getString("title")
            val contentType = obj.optString("contentType")
            val items = obj.getJSONArray("content")
            val inner = mutableListOf<HomeItem>()
            for (j in 0 until items.length()) {
                val item = items.getJSONObject(j)
                inner.add(HomeItem(item.optString("id"), item.optString("mobileCarouselImage")))
            }
            list.add(HomeSection(title, contentType, inner))
        }
        return list
    }
}