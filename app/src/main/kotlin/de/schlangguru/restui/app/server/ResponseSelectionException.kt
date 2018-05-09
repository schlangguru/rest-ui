package de.schlangguru.restui.server

class ResponseSelectionException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)

}