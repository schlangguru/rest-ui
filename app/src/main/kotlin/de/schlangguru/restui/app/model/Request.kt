package de.schlangguru.restui.app.model

data class Request (
        val host: String,
        val path: String,
        val method: String,
        val queryParameter: Map<String, String>,
        val headers: Map<String, String>,
        val entity: String
)