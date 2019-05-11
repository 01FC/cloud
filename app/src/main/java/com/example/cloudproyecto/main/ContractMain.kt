package com.example.cloudproyecto.main

import org.json.JSONArray

interface ContractMain {
    interface View {
        fun setDataFromPlant(dataJson:JSONArray)
    }
    interface Presenter {
        fun getDataFromPlant()
        fun startFan()
        fun stopFan()
        fun startLight()
        fun stopLight()
        fun startIrrigation()
        fun stopIrrigation()
    }
}