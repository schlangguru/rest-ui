package de.schlangguru.restui.app.server

import com.google.gson.Gson
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.model.Request

/**
 * Uses a script (JavaScript) provided by the user to determine the REST response.
 *
 * @property script The user-script on which this strategy relies on.
 */
class ScriptedResponseStrategy (val script: String = ""): ResponseStrategy {

    @Transient
    private val gson = Gson()
    @Transient
    private val REQUEST_VARIABLE_NAME = "_request"

    /**
     * Executes the user-script with the JavaScript "Nashorn" engine.
     * Injects the REST [request] as JS variable into the script and expects
     * the script to return a valid response name.
     * Takes the corresponding response from the [availableResponses] and returns it.
     */
    override fun provideResponse(request: Request, availableResponses: List<MockResponse>): MockResponse {
        val jsVariables = mapOf<String, String>(
                Pair(REQUEST_VARIABLE_NAME, gson.toJson(request))
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