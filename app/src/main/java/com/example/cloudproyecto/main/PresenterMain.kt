package com.example.cloudproyecto.main

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class PresenterMain(cView: ContractMain.View) : ContractMain.Presenter {
    private val mView: ContractMain.View = cView
    private val dataURLirrigation = "https://08day88hn6.execute-api.us-west-2.amazonaws.com/Examen001Stage/iot/data/hum"
    private val dataURLlight = "https://08day88hn6.execute-api.us-west-2.amazonaws.com/Examen001Stage/iot/data/temp"
    private val dataURLviento = "https://08day88hn6.execute-api.us-west-2.amazonaws.com/Examen001Stage/iot/data/dx"

    override fun startFan() {
        Fuel.post(dataURLviento)
            .jsonBody("{ \"viento\" : \"1\" }")
            .also { println(it) }
            .response { result ->
                val (bytes, error) = result
                if (error == null) {
                    println("RESPUESTA =======>  ${String(bytes!!)}")

                } else {
                    println("Error ========> $error")

                }
            }
    }

    override fun stopFan() {
        Fuel.post(dataURLviento)
            .jsonBody("{ \"viento\" : \"0\" }")
            .also { println(it) }
            .response { result ->
                val (bytes, error) = result
                if (error == null) {
                    println("RESPUESTA =======>  ${String(bytes!!)}")

                } else {
                    println("Error ========> $error")

                }
            }
    }

    override fun startLight() {
        Fuel.post(dataURLlight)
            .jsonBody("{ \"luz\" : \"1\" }")
            .also { println(it) }
            .response { result ->
                val (bytes, error) = result
                if (error == null) {
                    println("RESPUESTA =======>  ${String(bytes!!)}")

                } else {
                    println("Error ========> $error")

                }
            }

    }

    override fun stopLight() {
        Fuel.post(dataURLlight)
            .jsonBody("{ \"luz\" : \"0\" }")
            .also { println(it) }
            .response { result ->
                val (bytes, error) = result
                if (error == null) {
                    println("RESPUESTA =======>  ${String(bytes!!)}")

                } else {
                    println("Error ========> $error")

                }
            }
    }

    override fun getDataFromPlant() {
        TODO("Checar niveles de humedad y temperatura normales")
    }

    override fun startIrrigation() {

        Fuel.post(dataURLirrigation)
            .jsonBody("{ \"regar\" : \"1\" }")
            .also { println(it) }
            .response { result ->
                val (bytes, error) = result
                if (error == null) {
                    println("RESPUESTA =======>  ${String(bytes!!)}")

                } else {
                    println("ERROR =====> $error")

                }
            }
    }

    override fun stopIrrigation() {
        Fuel.post(dataURLirrigation)
            .jsonBody("{ \"regar\" : \"0\" }")
            .also { println(it) }
            .response { result ->
                val (bytes, error) = result
                if (error == null) {
                    println("RESPUESTA =======>  ${String(bytes!!)}")

                } else {
                    println("Error ========> $error")

                }
            }
    }

}