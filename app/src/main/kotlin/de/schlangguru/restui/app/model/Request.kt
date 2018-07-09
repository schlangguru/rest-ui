package de.schlangguru.restui.app.model

/**
 * Defines an HTTP request.
 */
data class Request (
        /** The requested host. */
        val host: String,
        /** The request path. */
        val path: String,
        /** The HTTP method of the request. */
        val method: String,
        /** The query parameters of the request. */
        val queryParameter: Map<String, String>,
        /** The HTTP headers. */
        val headers: Map<String, String>,
        /** The request entity. */
        val entity: String
)