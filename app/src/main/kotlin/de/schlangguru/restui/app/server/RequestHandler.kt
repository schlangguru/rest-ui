package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.AddRequestAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.app.model.RequestHeader
import org.glassfish.jersey.process.Inflector
import java.nio.charset.Charset
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.core.Response


class RequestHandler(
        private val mockResource: MockResource,
        private val store: AppStore = AppStore
) : Inflector<ContainerRequestContext, Any> {

    override fun apply(request: ContainerRequestContext): Response {

        store.dispatch(AddRequestAction(Request(
                host = request.uriInfo.baseUri.toString(),
                path = request.uriInfo.path,
                method = request.method,
                headers = request.headers.map { RequestHeader(it.key, it.value.joinToString()) },
                entity = request.entityStream.readBytes().toString(Charset.defaultCharset())
        )))

        val actionResponse = mockResource.responseStrategy.provideResponse(request, mockResource.responses)

        return Response.status(actionResponse.statusCode)
                .header("Content-Type", actionResponse.contentType)
                .entity(actionResponse.content.toByteArray())
                .build()
    }
}