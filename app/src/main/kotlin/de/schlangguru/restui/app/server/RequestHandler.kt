package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.AddRequestAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.Request
import org.glassfish.jersey.process.Inflector
import java.nio.charset.Charset
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.core.Response


class RequestHandler(
        private val mockResource: MockResource,
        private val store: AppStore = AppStore
) : Inflector<ContainerRequestContext, Any> {

    override fun apply(containerRequestContext: ContainerRequestContext): Response {

        val request = Request(
                host = containerRequestContext.uriInfo.baseUri.toString(),
                path = containerRequestContext.uriInfo.path,
                method = containerRequestContext.method,
                queryParameter = containerRequestContext.uriInfo.queryParameters.mapValues { it.value.joinToString() },
                headers = containerRequestContext.headers.mapValues { it.value.joinToString() },
                entity = containerRequestContext.entityStream.readBytes().toString(Charset.defaultCharset())
        )
        store.dispatch(AddRequestAction(request))

        val actionResponse = mockResource.responseStrategy.provideResponse(request, mockResource.responses)

        return Response.status(actionResponse.statusCode)
                .header("Content-Type", actionResponse.contentType)
                .entity(actionResponse.content.toByteArray())
                .build()
    }
}