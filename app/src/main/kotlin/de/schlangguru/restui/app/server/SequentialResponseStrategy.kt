package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.server.ResponseSelectionException
import javax.ws.rs.container.ContainerRequestContext

class SequentialResponseStrategy : ResponseStrategy {

    private var nextResponseIndex = 0

    override fun provideResponse(request: ContainerRequestContext, availableResponses: List<MockResponse>): MockResponse {
        if (availableResponses.isEmpty()) {
            throw ResponseSelectionException("No responses available")
        }

        if (nextResponseIndex >= availableResponses.size) {
            nextResponseIndex = 0
        }

        return availableResponses.elementAt(nextResponseIndex++)
    }

}