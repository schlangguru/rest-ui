package de.schlangguru.restui.app.server

import com.google.gson.Gson
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.server.ResponseSelectionException

class ScriptedResponseStrategy (private val script: String): ResponseStrategy {

    private val gson = Gson()

    override fun provideResponse(request: Request, availableResponses: List<MockResponse>): MockResponse {
        val jsVariables = mapOf<String, String>(
                Pair("_request", gson.toJson(request))
        )
        val jsEngine = JsScriptEngine(script, jsVariables)
        val responseName = jsEngine.invokeScript()

        val response = availableResponses.find { it.name == responseName }

        if (response != null) {
            return response
        } else {
            throw ResponseSelectionException("No matching response found with name $responseName")
        }
    }
}