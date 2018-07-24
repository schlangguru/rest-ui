package de.schlangguru.restui.app.actions

import de.schlangguru.restui.core.Action

class StartServerAction: Action

class StopServerAction: Action

class SaveStateAction (
        val filePath: String
) : Action