package de.schlangguru.restui.app

import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.ServerStatus

data class AppState (
        val serverStatus: ServerStatus = ServerStatus.Stopped,
        val host: String = "localhost",
        val port: Int = 7777,

        val requestInbox: List<Request> = emptyList(),
        val mockResources: List<MockResource> = emptyList()
)