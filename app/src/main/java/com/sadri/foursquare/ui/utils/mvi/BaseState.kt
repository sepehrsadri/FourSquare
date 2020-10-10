package com.sadri.foursquare.ui.utils.mvi

import com.sadri.foursquare.ui.navigation.NavigationCommand

data class BaseState(
    val stable: Boolean = true,
    val error: ErrorState = ErrorState.Disabled,
    val showLoading: Boolean = false,
    val coordinate: NavigationCommand = NavigationCommand.Nothing
) {

    sealed class ErrorState(val showSnackBar: Boolean) {
        data class String(val snackBarMessageString: kotlin.String) :
            ErrorState(showSnackBar = true)

        data class Res(val snackBarMessage: Int) : ErrorState(showSnackBar = true)
        data class UnAuthorized(val errorMessage: kotlin.String) : ErrorState(showSnackBar = false)
        object Disabled : ErrorState(showSnackBar = false)
    }


    companion object {
        fun stable() = BaseState()

        fun loading() = BaseState(
            stable = false,
            showLoading = true
        )

        fun showError(message: String) =
            BaseState(
                stable = false,
                error = ErrorState.String(
                    message
                )
            )

        fun coordinate(navigationCommand: NavigationCommand) =
            BaseState(
                stable = false,
                coordinate = navigationCommand
            )
    }
}
