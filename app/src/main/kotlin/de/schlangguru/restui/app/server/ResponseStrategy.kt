package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.model.Request

interface ResponseStrategy {

    fun provideResponse(request: Request, availableResponses: List<MockResponse>): MockResponse

}