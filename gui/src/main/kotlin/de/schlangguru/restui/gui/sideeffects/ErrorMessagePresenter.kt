package de.schlangguru.restui.gui.sideeffects

import de.schlangguru.restui.app.actions.ErrorMessageAction
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.SideEffect
import de.schlangguru.restui.gui.errorDialog
import javafx.application.Platform

class ErrorMessageHandler : SideEffect {

    override fun handle(action: Action) {
        when (action) {
            is ErrorMessageAction -> showErrorDialog(action.errorMessage, action.errorDetails)
        }
    }

    private fun showErrorDialog(message: String, details: String) {
        Platform.runLater {
            errorDialog(message, details)
        }
    }
}