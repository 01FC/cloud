package com.example.cloudproyecto.co2

import org.json.JSONArray

interface ContractDx {
    interface View {
        fun setData(dataListJson: JSONArray)
        fun requestData()
    }

    interface Presenter {
        fun getDataOnline()
    }
}