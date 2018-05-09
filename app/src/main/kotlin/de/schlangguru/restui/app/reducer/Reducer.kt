package de.schlangguru.restui.app.reducer

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.actions.AddRequestAction
import de.schlangguru.restui.app.actions.AddMockResourceAction
import de.schlangguru.restui.app.actions.ServerStatusChangedAction
import de.schlangguru.restui.app.actions.UpdateSettingsAction

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
}