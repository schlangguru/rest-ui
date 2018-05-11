package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResponse
import javax.ws.rs.container.ContainerRequestContext

interface ResponseStrategy {

    fun provideResponse(request: ContainerRequestContext, availableResponses: List<MockResponse>): MockResponse

}