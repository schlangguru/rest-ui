package de.schlangguru.restui.app.server

import javax.script.ScriptEngineManager

/**
 * Script engine used to allow responseStrategies based on javaScript.
 *
 * Uses the "Nashorn" engine to invoke a js script.
 */
class JsScriptEngine (
        private val userScript: String,
        private val contextVariables: Map<String, String> = emptyMap()
) {
    private val scriptEngineName = "nashorn"
    private val scriptEngine = ScriptEngineManager().getEngineByName(scriptEngineName)

    /**
     * Invokes the userScript of this engine.
     *
     * @return the return value of the userScript as String. Returns "" if the script does not return a value.
     */
    fun invokeScript(): String {
        initContextVariables()
        val result = scriptEngine.eval(userScript)

        return when(result) {
            is String -> result
            null -> ""
            else -> result.toString()
        }
    }

    /**
     * Initializes the variables of the script engine that can be used by the userScript.
     */
    private fun initContextVariables() {
        contextVariables.forEach {
            scriptEngine.eval("""var ${it.key} = ${it.value}""")
        }
    }
}