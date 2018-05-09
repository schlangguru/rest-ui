package de.schlangguru.restui.app.model

import de.schlangguru.restui.app.server.ResponseStrategy

data class MockResource (
        val path: String,
        val method: String,
        val responseStrategy: ResponseStrategy,
        val responses: Map<String, MockResourceResponse>
)

data class MockResourceResponse (
        val statusCode: Int,
        val contentType: String,
        val content: String
)
