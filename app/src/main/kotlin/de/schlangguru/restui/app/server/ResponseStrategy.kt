package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.model.Request

/**
 * Provides a strategy to return a specific response based on a given request.
 */
interface ResponseStrategy {

    /**
     * Returns a concrete [MockResponse] instance from the [availableResponses] ]based on the given [request].
     */
    fun provideResponse(request: Request, availableResponses: List<MockResponse>): MockResponse

}