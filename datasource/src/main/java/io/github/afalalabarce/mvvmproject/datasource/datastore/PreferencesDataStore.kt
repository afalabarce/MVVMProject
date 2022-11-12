package io.github.afalalabarce.mvvmproject.datasource.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PreferencesDataStore(
    private val context: Context
) {
    fun getDeviceId(): Flow<String> = this@PreferencesDataStore.context.dataStore.data.map { pref ->
        pref[stringPreferencesKey("DeviceId")] ?: ""
    }

    fun setDeviceId(deviceId: String) = CoroutineScope(Dispatchers.IO).launch {
        this@PreferencesDataStore.context.dataStore.edit { prefs ->
            val currentDeviceId = prefs[stringPreferencesKey("DeviceId")] ?: ""
            if (currentDeviceId.isNullOrEmpty())
                prefs[stringPreferencesKey("DeviceId")] = deviceId
        }
    }
}

private val Context.dataStore by preferencesDataStore("MVVMProjectAppPreferences")