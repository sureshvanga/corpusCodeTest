package com.example.corpuscodetest.others

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
object DataStoreManager {
    private lateinit var appContext: Context

    private val LOGIN_KEY = stringPreferencesKey("is_logged_in")

    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("user_prefs") }
        )
    }

    fun init(context: Context) {
        if (!::appContext.isInitialized) {
            appContext = context.applicationContext
        }
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        dataStore.edit { it[LOGIN_KEY] = loggedIn.toString() }
    }

    val isLoggedIn: Flow<Boolean>
        get() = dataStore.data.map { it[LOGIN_KEY]?.toBoolean() ?: false }
}
