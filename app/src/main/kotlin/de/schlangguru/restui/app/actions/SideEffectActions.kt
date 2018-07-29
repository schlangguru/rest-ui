package de.schlangguru.restui.app.actions

import de.schlangguru.restui.app.model.CertificateInfo
import de.schlangguru.restui.core.Action

class StartServerAction: Action

class StopServerAction: Action

class SaveStateAction (
        val filePath: String
) : Action

class GenerateCertificateAction (
        val keyStorePath: String,
        val certInfo: CertificateInfo
) : Action