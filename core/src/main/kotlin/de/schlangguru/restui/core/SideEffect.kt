package de.schlangguru.restui.core

import de.schlangguru.restui.core.Action

interface SideEffect {

    fun handle(action: Action)

}
