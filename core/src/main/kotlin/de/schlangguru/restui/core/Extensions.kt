package de.schlangguru.restui.core

import java.util.function.Predicate

inline fun <T> Iterable<T>.mapIf(predicate: (T) -> Boolean, map: (T) -> T): List<T> {
    return map { if (predicate(it)) map(it) else it }
}