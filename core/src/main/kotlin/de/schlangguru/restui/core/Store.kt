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

    /**
     * Registers a new [SideEffect] to handle
     * [Action] side effects.
     */
    fun register(sideEffect: SideEffect) {
        sideEffects.add(sideEffect)
    }

    /**
     * Registers a new [StateHandler] that will be
     * notified on application State updates.
     */
    fun register(stateHandler: StateHandler<T>) {
        stateHandlers.add(stateHandler)
        stateHandler.handle(currentState)
    }

    /**
     * Dispatches the given [action] to all reucers and [SideEffect]s.
     * The action will be dispatched asynchronously in a separate thread.
     */
    fun dispatch(action: Action) {
        dispatchThreadExecutor.execute {
            dispatchAndWait(action)
        }
    }

    /**
     * Dispatches the given [action] to all reucers and [SideEffect]s.
     * The action will be dispatched synchronously, leading to a blocking call of this method.
     */
    fun dispatchAndWait(action: Action) {
        val newState = reduce(action, currentState)
        stateHandlers.forEach { it.handle(newState) }
        sideEffects.forEach { it.handle(action) }
        this.currentState = newState
    }

    /**
     * Releases all resources of this store to shutdown the app properly.
     */
    fun shutdown() {
        dispatchThreadExecutor.shutdown()
    }

    /**
     * Reduces the current [state] with the given [action] to the
     * new State [T] of the application.
     */
    abstract fun reduce(action: Action, state: T): T
}