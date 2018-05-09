package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResource

interface RestServer {
    fun start(host: String, port: Int, mockResources: List<MockResource>)
    fun shutdown()
}