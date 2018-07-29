package de.schlangguru.restui.app

import java.io.File

fun String.runCommand(workingDir: File) {
    ProcessBuilder(*split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
}