package de.schlangguru.restui.app.model

import de.schlangguru.restui.app.server.ResponseStrategy
import java.util.*

/**
 * Defines a mocked REST resource.
 * The resource will be provided by the RestServer.
 */
data class MockResource (
        val id: String = UUID.randomUUID().toString(),
        /** The path to the resource. */
        val path: String,
        /** The HTTP method of the resource. */
        val method: String,
        /** The used [ResponseStrategy] to determine the response. */
        val responseStrategy: ResponseStrategy,
        /** The available responses for this resource. */
        val responses: List<MockResponse>
)

/**
 * Defines the response to a [MockResource].
 */
data class MockResponse (
        /** The name of the response. */
        val name: String,
        /** The HTTP status code. */
        val statusCode: Int,
        /** The contentType header field. */
        val contentType: String,
        /** The response entity. */
        val content: String
)