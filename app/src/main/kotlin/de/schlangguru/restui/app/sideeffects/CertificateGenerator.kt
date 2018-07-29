package de.schlangguru.restui.app.sideeffects

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.ErrorMessageAction
import de.schlangguru.restui.app.actions.GenerateCertificateAction
import de.schlangguru.restui.app.model.CertificateInfo
import de.schlangguru.restui.app.runCommand
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.SideEffect
import java.nio.file.Path
import java.nio.file.Paths

class CertificateGenerator(
        private val store: AppStore = AppStore
) : SideEffect {
    private val userHome = Paths.get(System.getProperty("user.home"))
    private val javaBinDir = Paths.get(System.getProperty("java.home")).parent.resolve("bin")
    private val keyToolName = if (isWindows()) "keytool.exe" else "keytool"
    private val pathToKeyTool: Path = javaBinDir.resolve(keyToolName)

    override fun handle(action: Action) {
        when (action) {
            is GenerateCertificateAction -> generateCertificate(action.certInfo, action.keyStorePath)
        }
    }

    fun generateCertificate(certInfo: CertificateInfo, keystorePath: String) {
        try {
            val command = "$pathToKeyTool -genkey -alias ${certInfo.alias} -keyalg RSA -keystore ${keystorePath} -keysize ${certInfo.keysize} -storepass ${certInfo.storePassword} -keypass ${certInfo.keyPassword} -dname ${certInfo.dName}"
            command.runCommand(userHome.toFile())
        } catch (e: Exception) {
            store.dispatch(ErrorMessageAction("Error during certificate creation", e.message ?: e.toString()))
        }
    }

    private fun isWindows(): Boolean {
        return System.getProperty("os.name").toLowerCase().contains("win")
    }
}