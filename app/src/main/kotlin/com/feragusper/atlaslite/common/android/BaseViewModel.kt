package com.feragusper.atlaslite.common.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.feragusper.atlaslite.common.exception.Failure

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}