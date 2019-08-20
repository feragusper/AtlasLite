package com.feragusper.atlaslite.common.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object ServerError : Failure()

    abstract class FeatureFailure : Failure()
}
