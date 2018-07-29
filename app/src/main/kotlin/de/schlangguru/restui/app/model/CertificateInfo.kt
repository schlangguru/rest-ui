package de.schlangguru.restui.app.model

data class CertificateInfo (
        val alias: String,
        val keysize: Int,
        val storePassword: String,
        val keyPassword: String,
        val dName: String
)