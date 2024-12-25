package com.example.lesson1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var addressHelper: AddressHelper
    private lateinit var provinceSpinner: Spinner
    private lateinit var districtSpinner: Spinner
    private lateinit var wardSpinner: Spinner
    private lateinit var dobButton: Button
    private lateinit var calendarView: CalendarView
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressHelper = AddressHelper(resources)

        provinceSpinner = findViewById(R.id.provinceSpinner)
        districtSpinner = findViewById(R.id.districtSpinner)
        wardSpinner = findViewById(R.id.wardSpinner)
        dobButton = findViewById(R.id.dobButton)
        calendarView = findViewById(R.id.calendarView)
        submitButton = findViewById(R.id.submitButton)

        loadProvinces()

        // Handle Date of Birth Button
        dobButton.setOnClickListener {
            calendarView.visibility = if (calendarView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        // Validate and Submit Form
        submitButton.setOnClickListener {
            val isFormValid = validateForm()
            if (isFormValid) {
                Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadProvinces() {
        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner.adapter = provinceAdapter

        provinceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                loadDistricts(selectedProvince)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected if needed
            }
        }
    }

    private fun loadDistricts(province: String) {
        val districts = addressHelper.getDistricts(province)
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtSpinner.adapter = districtAdapter

        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedDistrict = districts[position]
                loadWards(province, selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected if needed
            }
        }
    }


    private fun loadWards(province: String, district: String) {
        val wards = addressHelper.getWards(province, district)
        val wardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wardSpinner.adapter = wardAdapter
    }

    private fun validateForm(): Boolean {
        // Validate form fields (example: check if fields are filled, checkbox is checked)
        // Display validation messages
        return true
    }
}
