package de.schlangguru.restui.app

import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.ServerStatus

/**
 * The global state of the application.
 */
data class AppState (
        val serverStatus: ServerStatus = ServerStatus.Stopped,
        val host: String = "localhost",
        val port: Int = 7777,
        val useDarkTheme: Boolean = false,

        /** Stores all received requests. */
        val requestInbox: List<Request> = emptyList(),
        /** Stores all configured [MockResource]s. */
        val mockResources: List<MockResource> = emptyList()
)