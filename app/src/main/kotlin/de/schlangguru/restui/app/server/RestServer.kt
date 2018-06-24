package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResource

/**
 * REST Server to mock REST APIs.
 */
interface RestServer {
    /**
     * Starts the server with the given [host] and [port].
     * The given [mockResources] will be provided as REST resources.
     */
    fun start(host: String, port: Int, mockResources: List<MockResource>)

    /**
     * Stops the server.
     */
    fun shutdown()
}