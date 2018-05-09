package de.schlangguru.restui.app.model

data class Request (
        val host: String,
        val path: String,
        val method: String,
        val headers: List<RequestHeader>,
        val entity: String
)

data class RequestHeader (
    var name: String,
    var value: String
)