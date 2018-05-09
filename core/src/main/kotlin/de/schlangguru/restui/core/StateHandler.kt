package de.schlangguru.restui.core

interface StateHandler<T> {

    fun handle(state: T)

}
