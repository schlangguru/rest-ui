package de.schlangguru.restui.gui

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.actions.UpdateSettingsAction
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.SideEffect
import de.schlangguru.restui.core.StateHandler
import de.schlangguru.restui.gui.removeStylesheet
import javafx.application.Platform
import tornadofx.importStylesheet

/**
 * Responsible tho change the theme, if the settings get updated.
 */
class ThemeStateHandler : StateHandler<AppState> {

    private val DARK_THEME = "/css/modena_dark.css"

    override fun handle(state: AppState) {
        val useDarkTheme = state.useDarkTheme
        if (useDarkTheme) {
            Platform.runLater { importStylesheet(DARK_THEME) }
        } else {
            Platform.runLater { removeStylesheet(DARK_THEME) }
        }
    }

}