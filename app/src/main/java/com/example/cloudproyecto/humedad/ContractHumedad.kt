package com.example.cloudproyecto.humedad

import org.json.JSONArray

interface ContractHumedad {
    interface View {
        fun setData(dataListJson: JSONArray)
        fun requestData()
    }

    interface Presenter {
        fun getDataOnline()
    }
}