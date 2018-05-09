package de.schlangguru.restui.app.server

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.ServerStatusChangedAction
import de.schlangguru.restui.app.actions.StartServerAction
import de.schlangguru.restui.app.actions.StopServerAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.ServerStatus
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.SideEffect
import de.schlangguru.restui.core.StateHandler
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.model.Resource
import java.io.IOException
import java.net.URI


class RestServerImpl (
        private val store: AppStore = AppStore
): RestServer, SideEffect, StateHandler<AppState> {
    private var server: HttpServer? = null
    private var host: String = "localhost"
    private var port: Int = 8080
    private var mockResources: List<MockResource> = emptyList()

    init {
        store.register(this as SideEffect)
        store.register(this as StateHandler<AppState>)
    }

    override fun handle(action: Action) {
        when (action) {
            is StartServerAction -> start(this.host, this.port, this.mockResources)
            is StopServerAction -> shutdown()
        }
    }

    override fun handle(state: AppState) {
        host = state.host
        port = state.port
        mockResources = state.mockResources
    }

    override fun start(host: String, port: Int, mockResources: List<MockResource>) {
        try {
            server = GrizzlyHttpServerFactory.createHttpServer(URI("http://$host:$port"), resourceConfig(mockResources), false)
            server?.start()

            store.dispatch(ServerStatusChangedAction(ServerStatus.Started))
        } catch (e: IOException) {
            e.printStackTrace()
            store.dispatch(ServerStatusChangedAction(ServerStatus.Stopped))
        } catch (e: InterruptedException) {
            e.printStackTrace()
            store.dispatch(ServerStatusChangedAction(ServerStatus.Stopped))
        }
    }

    private fun resourceConfig(mockResources: List<MockResource>): ResourceConfig {
        val config = ResourceConfig()
        mockResources.forEach { action ->
            val resourceBuilder = Resource.builder().path("/")
            resourceBuilder.addChildResource(action.path).addMethod(action.method).handledBy(RequestHandler(action))

            config.registerResources(resourceBuilder.build())
        }

        return config
    }

    override fun shutdown() {
        store.dispatch(ServerStatusChangedAction(ServerStatus.Stopping))
        server?.shutdownNow()
        store.dispatch(ServerStatusChangedAction(ServerStatus.Stopped))
    }

}