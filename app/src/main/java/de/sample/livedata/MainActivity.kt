package de.sample.livedata

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelMain: ViewModelMain
    private var countNonViewModel = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelMain = ViewModelProviders.of(this).get(ViewModelMain::class.java)

        // Observe view model count changes and update textview
        viewModelMain.count.observe(this, Observer {
            tvCountViewModel.text = "ViewModel value: $it"
        })

        bnAddCount.setOnClickListener {

            // Update the view model based count value
            viewModelMain.count.postValue(
                viewModelMain.count.value?.plus(1)
            )

            // Update the activity based count value
            countNonViewModel++
            tvCountNonViewModel.text = "Activity value: $countNonViewModel"
        }

        //transformationMap()
        //transformationSwitchMap()
        distinctUntilChanged()
    }

    private fun transformationMap(){

        viewModelMain.mapped.observe(this, Observer { mappedValue ->

            Log.d("LIVE_DATA", "Mapped Value: $mappedValue")
        })

        viewModelMain.source.observe(this, Observer { sourceValue ->
            Log.d("LIVE_DATA", "Source Value: $sourceValue")
        })

        viewModelMain.source.postValue("New Source")
    }

    private fun transformationSwitchMap(){

        viewModelMain.source1.observe(this, Observer { sourceValue ->

            Log.d("LIVE_DATA", "Source Value: $sourceValue")
        })

        viewModelMain.switchMapped.observe(this, Observer { mappedValue ->

            Log.d("LIVE_DATA", "Switch Mapped Value: $mappedValue")
        })

        viewModelMain.switchMappedLvl2.observe(this, Observer {mappedValue ->

            Log.d("LIVE_DATA", "Switch Mapped Value: $mappedValue")
        })

        viewModelMain.source1.postValue("New Source1")
    }

    private fun distinctUntilChanged(){

        viewModelMain.source2.observe(this, Observer { sourceValue ->

            Log.d("LIVE_DATA", "Source Value: $sourceValue")
        })

        viewModelMain.distinctSource2.observe(this, Observer { distinctValue ->

            Log.d("LIVE_DATA", "Distinct Value: $distinctValue")
        })

        viewModelMain.source2.postValue("Initial Source2 - New")
    }
}
