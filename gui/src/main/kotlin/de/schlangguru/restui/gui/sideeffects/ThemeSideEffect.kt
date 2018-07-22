package de.schlangguru.restui.gui.sideeffects

import de.schlangguru.restui.app.actions.UpdateSettingsAction
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.SideEffect
import de.schlangguru.restui.gui.removeStylesheet
import javafx.application.Platform
import tornadofx.importStylesheet

/**
 * Responsible tho change the theme, if the settings get updated.
 */
class ThemeSideEffect : SideEffect {
    private val DARK_THEME = "/css/modena_dark.css"

    override fun handle(action: Action) {
       when (action) {
           is UpdateSettingsAction -> changeTheme(action)
       }
    }

    private fun changeTheme(action: UpdateSettingsAction) {
        val useDarkTheme = action.useDarkTheme ?: false
        if (useDarkTheme) {
            Platform.runLater { importStylesheet(DARK_THEME) }
        } else {
            Platform.runLater { removeStylesheet(DARK_THEME) }
        }
    }

}