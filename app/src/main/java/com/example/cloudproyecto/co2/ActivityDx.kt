package com.example.cloudproyecto.co2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cloudproyecto.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class ActivityDx : AppCompatActivity() , OnChartValueSelectedListener, ContractDx.View {

    private lateinit var mTempChart: LineChart
    lateinit var mPresenter: ContractDx.Presenter
    lateinit var mLineData: LineData
    lateinit var mLegend: Legend
    lateinit var mXdata: XAxis
    lateinit var mYdata: YAxis
    lateinit var mYdataRight: YAxis
    val timer = Timer()

    companion object {
        fun newInstance(ctx: Context): Intent {
            val tempActivity = Intent(ctx, ActivityDx::class.java)
            return tempActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dx)

        mTempChart = findViewById(R.id.line_chart_dx)
        mTempChart.setOnChartValueSelectedListener(this)
        mPresenter = PresenterDx(this)
        //mPresenter.getDataOnline()
        //doAsync {
        timer.scheduleAtFixedRate(100, 5000) {
            println("Actualizando ...")
            mPresenter.getDataOnline()
        }
        //}
    }

    override fun setData(dataListJson: JSONArray) {
        paintChartData()
        for (i in 0 until dataListJson.length()) {
            println("CO2 ==> ${(dataListJson[i] as JSONObject).get("co2")}")
            addEntry((dataListJson[i] as JSONObject).get("co2").toString().toFloat())
        }
    }

    override fun requestData() {
        TODO("hacer tarea que pida data cada cierto tiempo y pinte")
    }

    private fun paintChartData() {
        // enable scaling and dragging
        val descr = Description()
        descr.text = "Datos de CO2 recabados."
        mTempChart.description.isEnabled = true
        mTempChart.description = descr
        mTempChart.setTouchEnabled(true)
        mTempChart.isDragEnabled = true
        mTempChart.setScaleEnabled(true)
        mTempChart.setDrawGridBackground(false)
        // if disabled, scaling can be done on x- and y-axis separately
        mTempChart.setPinchZoom(true)
        // set an alternative background color
        mTempChart.setBackgroundColor(Color.TRANSPARENT)
        mLineData = LineData()
        mLineData.setValueTextColor(Color.DKGRAY)
        // add empty data
        mTempChart.data = mLineData
        // get the legend (only possible after setting data)
        mLegend = mTempChart.legend
        // modify the legend ...
        mLegend.form = Legend.LegendForm.LINE
        mLegend.textColor = Color.DKGRAY

        mXdata = mTempChart.xAxis
        mXdata.textColor = Color.DKGRAY
        mXdata.setDrawGridLines(false)
        mXdata.setAvoidFirstLastClipping(true)
        mXdata.isEnabled = true

        mYdata = mTempChart.axisLeft
        mYdata.textColor = Color.DKGRAY
        mYdata.axisMaximum = 50.0f
        mYdata.axisMinimum = 0.0f
        mYdata.setDrawGridLines(true)

        mYdataRight = mTempChart.axisRight
        mYdataRight.isEnabled = false

    }

    private fun addEntry(yiData: Float) {
        val data = mTempChart.data
        if (data != null) {
            var set: ILineDataSet? = data.getDataSetByIndex(0)
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(
                Entry(set!!.entryCount.toFloat(), yiData),
                0
            ) // TODO("poner en y, el valor de la temperatura")
            // let the chart know it's data has changed
            mTempChart.notifyDataSetChanged()
            // limit the number of visible entries
            mTempChart.setVisibleXRangeMaximum(120f)
            // move to the latest entry
            mTempChart.moveViewToX(data.entryCount.toFloat())
            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private fun createSet(): ILineDataSet? {
        val set: ILineDataSet? = LineDataSet(null, "CO2 data")
        (set as LineDataSet).color = Color.RED
        set.setCircleColor(Color.DKGRAY)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.valueTextColor = Color.DKGRAY
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }

    override fun onBackPressed() {
        timer.cancel()
        super.onBackPressed()
    }
    /**
     * Interface OnChartSelectedListener methods
     * */
    override fun onNothingSelected() {
        //println("Touched")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        println("Touched")
    }
}
