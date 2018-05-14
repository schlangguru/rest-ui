package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.model.Request
import javafx.beans.property.ReadOnlyListWrapper
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class RequestViewModel: ItemViewModel<Request>() {
    val host = bind (Request::host)
    val path = bind (Request::path)
    val method = bind (Request::method)
    val query = bind { SimpleStringProperty(queryString(item?.queryParameter ?: emptyMap())) }
    val headers = bind { ReadOnlyListWrapper<Map.Entry<String, String>>(item?.headers?.entries?.toList()?.observable()) }
    val entity = bind (Request::entity)

    private fun queryString(queryParameter: Map<String, String>): String {
        return queryParameter.entries.joinToString(", ") { "${it.key}=${it.value}" }
    }
}

class RequestHeaderViewModel: ItemViewModel<Map.Entry<String, String>>() {
    val name = bind (Map.Entry<String, String>::key)
    val value = bind (Map.Entry<String, String>::value)
}