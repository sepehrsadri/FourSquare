package com.sadri.foursquare.ui.utils.mvi

data class BaseState(
    val stable: Boolean = true,
    val message: Message = Message.Disabled,
    val showLoading: Boolean = false
) {

    sealed class Message {
        data class SnackBarString(val snackBarMessageString: kotlin.String) : Message()
        data class SnackBarRes(val snackBarMessage: Int) : Message()
        data class ToastString(val message: String) : Message()
        data class ToastRes(val message: Int) : Message()
        object Disabled : Message()
    }


    companion object {
        fun stable() = BaseState()

        fun loading() = BaseState(
            stable = false,
            showLoading = true
        )

        fun showSnackbar(message: String) =
            BaseState(
                stable = false,
                message = Message.SnackBarString(
                    message
                )
            )

        fun showSnackbar(message: Int) =
            BaseState(
                stable = false,
                message = Message.SnackBarRes(
                    message
                )
            )


        fun showToast(message: String) =
            BaseState(
                stable = false,
                message = Message.ToastString(
                    message
                )
            )

        fun showToast(message: Int) =
            BaseState(
                stable = false,
                message = Message.ToastRes(
                    message
                )
            )
    }
}
