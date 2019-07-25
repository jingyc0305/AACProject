package com.example.aac_library.base.event

/**
 * 向view层传递action的model
 */
class BaseEvent {

    var message: String? = null
    var action: Int? = null

    companion object {
        const val SHOW_LOADING_DIALOG = 1

        const val DISMISS_LOADING_DIALOG = 2

        const val SHOW_TOAST = 3

        const val FINISH = 4

        const val FINISH_WITH_RESULT_OK = 5
    }
}
