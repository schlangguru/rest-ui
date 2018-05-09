package de.schlangguru.restui.core

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors

abstract class Store<T>(
        initialState: T,
        private val sideEffects: CopyOnWriteArrayList<SideEffect> = CopyOnWriteArrayList(),
        private val stateHandlers: CopyOnWriteArrayList<StateHandler<T>> = CopyOnWriteArrayList()
) {
    private val dispatchThreadExecutor = Executors.newSingleThreadExecutor()
    private var currentState = initialState

    fun register(sideEffect: SideEffect) {
        sideEffects.add(sideEffect)
    }

    fun register(stateHandler: StateHandler<T>) {
        stateHandlers.add(stateHandler)
        stateHandler.handle(currentState)
    }

    fun dispatch(action: Action) {
        dispatchThreadExecutor.execute {
            val newState = reduce(action, currentState)
            stateHandlers.forEach { it.handle(newState) }
            sideEffects.forEach { it.handle(action) }
            this.currentState = newState
        }
    }

    abstract fun reduce(action: Action, state: T): T
}