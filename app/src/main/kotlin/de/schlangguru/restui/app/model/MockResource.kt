package de.schlangguru.restui.app.model

import de.schlangguru.restui.app.server.ResponseStrategy
import java.util.*

data class MockResource (
        val id: String = UUID.randomUUID().toString(),
        val path: String,
        val method: String,
        val responseStrategy: ResponseStrategy,
        val responses: List<MockResponse>
)

data class MockResponse (
        val name: String,
        val statusCode: Int,
        val contentType: String,
        val content: String
)