package de.schlangguru.restui.app.reducer

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.actions.*

class Reducer {

    fun reduce(action: ServerStatusChangedAction, state: AppState): AppState {
        return state.copy(serverStatus = action.ServerStatus)
    }

    fun reduce(action: UpdateSettingsAction, state: AppState): AppState {
        return state.copy(
                host = action.host ?: state.host,
                port = action.port ?: state.port
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
}