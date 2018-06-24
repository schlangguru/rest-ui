package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.AddRequestAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.Request
import org.glassfish.jersey.process.Inflector
import java.nio.charset.Charset
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.core.Response

/**
 * The [RequestHandler] is responsible for the request processing of a specific REST resource.
 *
 * @property mockResource The REST resource to handle the requests for.
 * @property store The main app store.
 */
class RequestHandler(
        private val mockResource: MockResource,
        private val store: AppStore = AppStore
) : Inflector<ContainerRequestContext, Any> {

    /**
     * Applies the request and provides the appropriate HTTP response.
     * This method notifies the UI about the new incoming reqeust, determines the
     * correct response based on the [ResponseStrategy] of the [MockResource] and
     * returns it as HTTP response.
     */
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