package com.example.cloudproyecto.co2

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class PresenterDx(cView: ContractDx.View) :ContractDx.Presenter{
    private var mView: ContractDx.View = cView
    private val dataURL = "https://08day88hn6.execute-api.us-west-2.amazonaws.com/Examen001Stage/iot/data/dx"

    override fun getDataOnline() {
        doAsync {
            val result = URL(dataURL).readText()

            uiThread {
                val readData = parseToJson(result) // JSON Array
                val jsonItems = (readData?.get("body")) as JSONArray
                /*println(jsonItems)
                for (i in 0 until jsonItems.length()) {
                    println("Temperature ==> ${(jsonItems[i] as JSONObject).get("temperature")}")
                }*/
                mView.setData(jsonItems)
            }
        }
    }

    private fun parseToJson(json: String?): JSONObject? {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(json)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }
}