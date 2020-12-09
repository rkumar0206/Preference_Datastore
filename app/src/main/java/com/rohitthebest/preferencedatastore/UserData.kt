package com.rohitthebest.preferencedatastore

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.map

data class UserInfo(val name: String, val age: Int, val isMale: Boolean)

class UserData(context: Context) {

    //creating dataStore
    private val dataStore = context.createDataStore("pref_name")

    //creating some keys
    companion object {

        val USER_AGE_KEY = preferencesKey<Int>("user_age_key")
        val USER_NAME_KEY = preferencesKey<String>("user_name_key")
        val USER_GENDER_KEY = preferencesKey<Boolean>("user_gender_key")
    }

    //storing user data
    suspend fun storeData(name: String, age: Int, isMale: Boolean) {

        dataStore.edit {

            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
            it[USER_GENDER_KEY] = isMale
        }
    }

    //creating user flow
    val userFlow = dataStore.data.map {

        val age = it[USER_AGE_KEY] ?: 0

        if (age < 18) {

            Toast.makeText(context, "Under Age", Toast.LENGTH_SHORT).show()
        }

        val name = it[USER_NAME_KEY] ?: ""
        val isMale = it[USER_GENDER_KEY] ?: false

        UserInfo(
                name,
                age,
                isMale
        )
    }
}