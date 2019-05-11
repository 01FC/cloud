package com.example.cloudproyecto.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cloudproyecto.R
import com.example.cloudproyecto.co2.ActivityDx
import com.example.cloudproyecto.humedad.ActivityHumedad
import com.example.cloudproyecto.temperatura.ActivityTemperature
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray


class MainActivity : AppCompatActivity(), View.OnClickListener, ContractMain.View {

    private lateinit var mPresenter: ContractMain.Presenter
    private lateinit var mImageView: ImageView
    private lateinit var mFanButton: FloatingActionButton
    private lateinit var mLightButton: FloatingActionButton
    private lateinit var mWaterButton: FloatingActionButton
    private lateinit var mStatsButton: FloatingActionButton
    private lateinit var mHum: FloatingActionButton
    private lateinit var mTmp: FloatingActionButton
    private lateinit var mCo2: FloatingActionButton
    private var fan = false
    private var light = false
    private var water = false
    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = PresenterMain(this)

        mImageView = iv_image_main
        mFanButton = fb_button_fan
        mLightButton = fb_button_light
        mWaterButton = fb_button_water
        mStatsButton = fb_button_stats
        mHum = fb_hum
        mTmp = fb_temp
        mCo2 = fb_co2

        mFanButton.setOnClickListener(this)
        mLightButton.setOnClickListener(this)
        mWaterButton.setOnClickListener(this)
        mStatsButton.setOnClickListener(this)
        mHum.setOnClickListener(this)
        mTmp.setOnClickListener(this)
        mCo2.setOnClickListener(this)
    }

    /**
     * Views onClick listener
     * */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fb_button_fan -> {
                fan = !fan
                if (fan) {
                    mFanButton.setImageDrawable(resources.getDrawable(R.drawable.fanon, this.theme))
                    AlertDialog.Builder(this)
                        .setTitle("Activar viento.")
                        .setMessage("Seguro que desea activar el viento?\nRecuerde desactivarlo!!")
                        .setPositiveButton("OK") { _, _ ->
                            mPresenter.startFan()
                        }.setNegativeButton("Cancelar") { _, _ ->
                            fan = !fan
                            mFanButton.setImageDrawable(resources.getDrawable(R.drawable.fanoff, this.theme))
                        }.show()
                } else {
                    mFanButton.setImageDrawable(resources.getDrawable(R.drawable.fanoff, this.theme))
                    AlertDialog.Builder(this)
                        .setTitle("Desactivar viento.")
                        .setMessage("Seguro que desea apagar el viento?")
                        .setPositiveButton("OK") { _, _ ->
                            mPresenter.stopFan()
                        }.setNegativeButton("Cancelar") { _, _ ->
                            fan = !fan
                            mFanButton.setImageDrawable(resources.getDrawable(R.drawable.fanon, this.theme))
                        }.show()
                }
            }
            R.id.fb_button_light -> {
                light = !light
                if (light) {
                    mLightButton.setImageDrawable(resources.getDrawable(R.drawable.lightbulbon, this.theme))
                    AlertDialog.Builder(this)
                        .setTitle("Activar luz.")
                        .setMessage("Seguro que desea activar la luz?\nRecuerde desactivarla!!")
                        .setPositiveButton("OK") { _, _ ->
                            mPresenter.startLight()
                        }.setNegativeButton("Cancelar") { _, _ ->
                            light = !light
                            mLightButton.setImageDrawable(resources.getDrawable(R.drawable.lightbulboff, this.theme))
                        }.show()
                } else {
                    mLightButton.setImageDrawable(resources.getDrawable(R.drawable.lightbulboff, this.theme))
                    AlertDialog.Builder(this)
                        .setTitle("Desactivar luz.")
                        .setMessage("Seguro que desea apagar la luz?")
                        .setPositiveButton("OK") { _, _ ->
                            mPresenter.stopLight()
                        }.setNegativeButton("Cancelar") { _, _ ->
                            light = !light
                            mLightButton.setImageDrawable(resources.getDrawable(R.drawable.lightbulbon, this.theme))
                        }.show()
                }
            }
            R.id.fb_button_water -> {
                water = !water
                if (water) {
                    mWaterButton.setImageDrawable(resources.getDrawable(R.drawable.wateron, this.theme))
                    AlertDialog.Builder(this)
                        .setTitle("Activar riego.")
                        .setMessage("Seguro que desea activar el riego?\nRecuerde desactivarlo!!")
                        .setPositiveButton("OK") { _, _ ->
                            mPresenter.startIrrigation()
                        }.setNegativeButton("Cancelar") { _, _ ->
                            water = !water
                            mWaterButton.setImageDrawable(resources.getDrawable(R.drawable.wateroff, this.theme))
                        }.show()
                } else {
                    mWaterButton.setImageDrawable(resources.getDrawable(R.drawable.wateroff, this.theme))
                    AlertDialog.Builder(this)
                        .setTitle("Desactivar riego.")
                        .setMessage("Seguro que desea apagar el riego?")
                        .setPositiveButton("OK") { _, _ ->
                            mPresenter.stopIrrigation()
                        }.setNegativeButton("Cancelar") { _, _ ->
                            water = !water
                            mWaterButton.setImageDrawable(resources.getDrawable(R.drawable.wateron, this.theme))
                        }.show()
                }
            }

            R.id.fb_button_stats -> {
                animateFAB()
            }

            R.id.fb_co2 -> {
                val intent = ActivityDx.newInstance(this)
                startActivity(intent)
            }

            R.id.fb_hum -> {
                val intent = ActivityHumedad.newInstance(this)
                startActivity(intent)
            }

            R.id.fb_temp -> {
                val intent = ActivityTemperature.newInstance(this)
                startActivity(intent)
            }
        }
    }

    private fun animateFAB() {
        if (isFabOpen) {
            mCo2.visibility = View.GONE
            mHum.visibility = View.GONE
            mTmp.visibility = View.GONE
            mCo2.isClickable = false
            mHum.isClickable = false
            mTmp.isClickable = false
            mStatsButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_backward))
            mCo2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_close))
            mHum.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_close))
            mTmp.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_close))
            isFabOpen = false
        } else {
            mCo2.visibility = View.VISIBLE
            mHum.visibility = View.VISIBLE
            mTmp.visibility = View.VISIBLE
            mCo2.isClickable = true
            mHum.isClickable = true
            mTmp.isClickable = true
            mStatsButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_forward))
            mCo2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_open))
            mHum.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_open))
            mTmp.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_open))
            isFabOpen = true
        }
    }

    override fun setDataFromPlant(dataJson: JSONArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

