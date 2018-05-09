package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.model.MockResourceResponse
import javax.ws.rs.container.ContainerRequestContext

class ScriptedResponseStrategy : ResponseStrategy {

    override fun provideResponse(request: ContainerRequestContext, availableResponses: Map<String, MockResourceResponse>): MockResourceResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun provideResponse(request: ContainerRequestContext, availableResponses: Map<String, MockResourceResponse>): MockResourceResponse {
//        val objMapper = ObjectMapper()
//        val nashorn = ScriptEngineManager().getEngineByName("nashorn")
//
//        nashorn.eval("var dispatch = new Function('_responses', '${action.responseDispatchScript}')")
//
//        val responses = objMapper.writeValueAsString(action.responses)
//        nashorn.eval("dispatch($responses)")
//
//        return null
//    }
//
//    private fun printPathParams(request: ContainerRequestContext) {
//        println(request.uriInfo.pathParameters.getFirst("param"))
//    }
//
//    private fun printHeaders(request: ContainerRequestContext) {
//        request.headers.forEach({ entry ->
//            val headerName = entry.key
//            val headerValue = entry.value.joinToString()
//            println("$headerName: $headerValue")
//        })
//    }
}