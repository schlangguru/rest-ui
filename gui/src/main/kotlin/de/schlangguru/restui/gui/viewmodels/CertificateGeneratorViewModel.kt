package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.GenerateCertificateAction
import de.schlangguru.restui.app.model.CertificateInfo
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class CertificateGeneratorViewModel(
        private val store: AppStore = AppStore
): ViewModel() {
    val alias = SimpleStringProperty("restui")
    val keysize = SimpleIntegerProperty(4096)
    val storePassword = SimpleStringProperty("changeme")
    val keyPassword = SimpleStringProperty("changeme")
    val dName = SimpleStringProperty("CN=restui")
    val keystorePath = SimpleStringProperty("")

    val availableKeySizes = listOf(1024, 2048, 4096, 8196)

    override fun onCommit() {
        val certInfo = CertificateInfo(
                alias.value,
                keysize.value,
                storePassword.value,
                keyPassword.value,
                dName.value
        )
        store.dispatch(GenerateCertificateAction(
                keystorePath.value,
                certInfo
        ))
    }
}