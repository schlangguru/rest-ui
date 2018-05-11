package de.schlangguru.restui.app.actions

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResponse
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

class UpdateMockResourceResponseAction (
        val mockResourceID: String,
        val response: MockResponse
): Action

class UpdateSettingsAction (
        val host: String? = null,
        val port: Int? = null
): Action