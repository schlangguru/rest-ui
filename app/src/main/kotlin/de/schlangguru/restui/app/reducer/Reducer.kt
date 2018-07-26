package de.schlangguru.restui.app.reducer

import com.thoughtworks.xstream.XStream
import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.actions.*
import de.schlangguru.restui.core.mapIf
import java.io.File

class Reducer {

    fun reduce(action: ServerStatusChangedAction, state: AppState): AppState {
        return state.copy(serverStatus = action.ServerStatus)
    }

    fun reduce(action: UpdateSettingsAction, state: AppState): AppState {
        return state.copy(
                host = action.host ?: state.host,
                port = action.port ?: state.port,
                useDarkTheme = action.useDarkTheme ?: state.useDarkTheme
        )
    }

    fun reduce(action: AddRequestAction, state: AppState): AppState {
        return state.copy(
                requestInbox = state.requestInbox + action.request
        )
    }

    fun reduce(action: AddMockResourceAction, state: AppState): AppState {
        return state.copy(
                mockResources = state.mockResources + action.mockResource
        )
    }

    fun reduce(action: RemoveMockResourceAction, state: AppState): AppState {
        return state.copy(
                mockResources = state.mockResources - action.mockResource
        )
    }

    fun reduce(action: LoadStateAction): AppState {
        val serializedState = File(action.filePath).readText()
        return XStream().fromXML(serializedState) as AppState
    }

    fun reduce(action: UpdateMockResourceAction, state: AppState): AppState {
        val updatedMockResources = state.mockResources.mapIf({ it.id == action.mockResource.id }) {
            action.mockResource
        }

        return state.copy(mockResources = updatedMockResources)
    }
}