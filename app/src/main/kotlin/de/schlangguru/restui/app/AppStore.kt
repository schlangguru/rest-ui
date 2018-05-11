package de.schlangguru.restui.app

import de.schlangguru.restui.app.actions.*
import de.schlangguru.restui.app.reducer.Reducer
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.Store

object AppStore: Store<AppState>(
        initialState = AppState()
) {
    private val mainReducer = Reducer()

    override fun reduce(action: Action, state: AppState): AppState {
        return when (action) {
            is ServerStatusChangedAction -> mainReducer.reduce(action, state)
            is UpdateSettingsAction -> mainReducer.reduce(action, state)
            is AddRequestAction -> mainReducer.reduce(action, state)
            is AddMockResourceAction -> mainReducer.reduce(action, state)
            is RemoveMockResourceAction -> mainReducer.reduce(action, state)
            is UpdateMockResourceAction -> mainReducer.reduce(action, state)
            else -> state
        }
    }
}