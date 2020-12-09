package com.rohitthebest.preferencedatastore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.rohitthebest.preferencedatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userData: UserData
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = UserData(this)

        binding.saveBtn.setOnClickListener {

            if (checkNullOrEmpty()) {

                val name = binding.nameET.text.toString().trim()
                val age = binding.ageET.text.toString().trim().toInt()
                val isMale = binding.genderSwitch.isChecked

                GlobalScope.launch {

                    userData.storeData(name, age, isMale)
                }
            } else {

                Toast.makeText(this, "fields can't be empty", Toast.LENGTH_SHORT).show()
            }

        }

        observeData()
    }

    private fun checkNullOrEmpty(): Boolean {

        if (binding.nameET.text.toString().trim().isEmpty()) {
            return false
        }

        if (binding.ageET.text.toString().trim().isEmpty()) {

            return false
        }

        return binding.nameET.text.toString().trim().isNotEmpty()
                && binding.ageET.text.toString().trim().isNotEmpty()
    }

    private fun observeData() {

        //converting the flow to live data and observing the data
        userData.userFlow.asLiveData().observe(this) {

            binding.nameTV.text = it.name
            binding.ageTV.text = it.age.toString()
            binding.genderSwitch.isChecked = it.isMale
        }
    }
}