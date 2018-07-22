package de.schlangguru.restui.app.actions

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.app.model.ServerStatus
import de.schlangguru.restui.core.Action

class ServerStatusChangedAction (
        val ServerStatus: ServerStatus
): Action

class AddRequestAction (
        val request: Request
): Action

class AddMockResourceAction (
        val mockResource: MockResource
): Action

class RemoveMockResourceAction (
        val mockResource: MockResource
): Action

class UpdateMockResourceAction (
        val mockResource: MockResource
): Action

class UpdateSettingsAction (
        val host: String? = null,
        val port: Int? = null,
        val useDarkTheme: Boolean? = null
): Action