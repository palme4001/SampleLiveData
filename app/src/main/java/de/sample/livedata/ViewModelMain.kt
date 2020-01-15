package de.sample.livedata

import android.app.Application
import androidx.lifecycle.*

class ViewModelMain(application: Application): AndroidViewModel(application) {

    // This value of the LiveData object will survive any configuration changes
    // (Activity restarts)
    val count = MutableLiveData(0)

    /**
     *  Live Data Transformations
     */

    /*
        Map
     */
    val source = MutableLiveData<String>("Initial Source")

    val mapped = Transformations.switchMap(source) { sourceValue ->
        MutableLiveData(sourceValue.plus(" - Mapped"))
    }

    /*
        Switch Map
     */
    val source1 = MutableLiveData<String>("Initial Source1")

    //
    val switchMapped = Transformations.switchMap(source1) { source1Value ->

        // This mutable live data object would be returned by a data repository or service
        MutableLiveData<String>("$source1Value SwitchMapped")
    }

    val switchMappedLvl2 = Transformations.switchMap(switchMapped) { switchMappedValue ->

        MutableLiveData<String>("$switchMappedValue Level2")
    }

    /*
        distinctUntilChanged
     */
    val source2 = MutableLiveData<String>("Initial Source2")

    // Creates a new LiveData Object with the initial value as the passed source LiveData
    // The new LiveData object only triggers, if the value of the source changes (in comparison
    // to it's old value)
    // If source value is given the exact same value as before, the new LiveData object will not be triggered
    val distinctSource2 = Transformations.distinctUntilChanged(source2)


    /*
        MediatorLiveData
        reacts to changes from multiple sources
     */
    val sourceMediator1 = MutableLiveData<String>("Mediator Source 1")
    val sourceMediator2 = MutableLiveData<String>("Mediator Source 2")

    val mediator = MediatorLiveData<String>().also { mediatorLiveData ->
        mediatorLiveData.addSource(sourceMediator1) { sourceMediator1Value ->
            mediatorLiveData.value = sourceMediator1Value
        }
        mediatorLiveData.addSource(sourceMediator2) {sourceMediator2Value ->
            mediatorLiveData.value = sourceMediator2Value
        }
    }
}