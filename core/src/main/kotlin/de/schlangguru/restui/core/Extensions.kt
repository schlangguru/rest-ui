package de.schlangguru.restui.core

/**
 * Conditional map.
 * Applies the function [map] on every element of the iterable if [predicate] returns true.
 */
inline fun <T> Iterable<T>.mapIf(predicate: (T) -> Boolean, map: (T) -> T): List<T> {
    return map { if (predicate(it)) map(it) else it }
}