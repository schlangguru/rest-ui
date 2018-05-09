package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.model.Request
import tornadofx.ItemViewModel
import tornadofx.observable

class RequestViewModel : ItemViewModel<Request>() {
    val host = bind (Request::host)
    val path = bind (Request::path)
    val method = bind (Request::method)
    val headers = item?.headers?.observable()
    val entity = bind (Request::entity)
}