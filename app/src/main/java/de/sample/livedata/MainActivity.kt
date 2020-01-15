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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelMain = ViewModelProviders.of(this).get(ViewModelMain::class.java)

        // Observe view model count changes and update textview
        viewModelMain.count.observe(this, Observer {
            tvCountViewModel.text = "Value: $it"
        })

        bnAddCount.setOnClickListener {

            // Update the view model based count value
            viewModelMain.count.postValue(
                viewModelMain.count.value?.plus(1)
            )
        }

        // Testing Transformations.map()
        transformationMap()

        // Testing Transformations.switchMap()
        transformationSwitchMap()

        // Testing Transformations.distinctUntilChanged()
        distinctUntilChanged()

        // Testing MediatorLiveData
        mediatorLiveData()
    }

    private fun transformationMap(){

        viewModelMain.mapped.observe(this, Observer { mappedValue ->

            Log.d("LIVE_DATA", "Mapped Value: $mappedValue")
        })

        viewModelMain.source.observe(this, Observer { sourceValue ->
            Log.d("LIVE_DATA", "Source Value: $sourceValue")
        })

        // This change will trigger the Observers of 'source' and 'mapped'
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

        // This will trigger all 3 LiveData Observers of:
        // source1
        //   -> switchMapped
        //      -> switchMappedLvl2
        viewModelMain.source1.postValue("New Source1")
    }

    private fun distinctUntilChanged(){

        viewModelMain.source2.observe(this, Observer { sourceValue ->

            Log.d("LIVE_DATA", "Source Value: $sourceValue")
        })

        viewModelMain.distinctSource2.observe(this, Observer { distinctValue ->

            Log.d("LIVE_DATA", "Distinct Value: $distinctValue")
        })

        // This will not trigger the Observer's of distinctSource2
        viewModelMain.source2.postValue("Initial Source2")

        // But this will ..
        viewModelMain.source2.postValue("Initial Source2 - New")
    }

    private fun mediatorLiveData() {

        viewModelMain.mediator.observe(this, Observer { mediatorValue ->
            Log.d("LIVE_DATA", "Mediator value: $mediatorValue")
        })

        // This will trigger the mediator LiveData object Observers
        viewModelMain.sourceMediator1.postValue("Mediator Source 1 - New")

        // Its also possible to directly change the value of the mediator Live Data
        viewModelMain.mediator.postValue("New Mediator Value")
    }
}
