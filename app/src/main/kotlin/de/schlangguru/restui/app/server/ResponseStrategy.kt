package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResourceResponse
import javax.ws.rs.container.ContainerRequestContext

interface ResponseStrategy {

    fun provideResponse(request: ContainerRequestContext, availableResponses: Map<String, MockResourceResponse>): MockResourceResponse

}