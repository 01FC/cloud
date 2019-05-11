package com.example.cloudproyecto.temperatura

import org.json.JSONArray

interface ContractTemperature {
    interface View {
        fun setData(dataListJson: JSONArray)
        fun requestData()
    }

    interface Presenter {
        fun getDataOnline()
    }
}