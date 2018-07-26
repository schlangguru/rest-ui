package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.model.Request

/**
 * Returns the available responses in sequential order.
 */
class SequentialResponseStrategy : ResponseStrategy {

    /** The index of the next response to return. */
    private var nextResponseIndex = 0

    /**
     * Takes the next response from [availableResponses] and returns it.
     * The [request] itself is ignored here.
     */
    override fun provideResponse(request: Request, availableResponses: List<MockResponse>): MockResponse {
        if (availableResponses.isEmpty()) {
            throw ResponseSelectionException("No responses available")
        }

        if (nextResponseIndex >= availableResponses.size) {
            nextResponseIndex = 0
        }

        return availableResponses.elementAt(nextResponseIndex++)
    }

}