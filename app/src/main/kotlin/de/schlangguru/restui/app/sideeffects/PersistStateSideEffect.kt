package de.schlangguru.restui.app.sideeffects

import com.google.gson.Gson
import com.thoughtworks.xstream.XStream
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.SaveStateAction
import de.schlangguru.restui.core.Action
import de.schlangguru.restui.core.SideEffect
import java.io.File

class PersistStateSideEffect(
        private val store: AppStore = AppStore
) : SideEffect {
    override fun handle(action: Action) {
        when (action) {
            is SaveStateAction -> saveStateToDisk(action)
        }
    }

    private fun saveStateToDisk(action: SaveStateAction) {
        val serializedState = XStream().toXML(store.currentState())
        File(action.filePath).writeText(serializedState)
    }

}