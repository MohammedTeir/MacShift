// File: ShellUtils.kt 
package com.macshift.utils

import java.io.BufferedReader 
import java.io.InputStreamReader

object ShellUtils { /** Checks for root by executing a simple command through su */ fun hasRoot(): Boolean { return try { val proc = Runtime.getRuntime().exec(arrayOf("su", "-c", "id")) proc.waitFor() proc.exitValue() == 0 } catch (e: Exception) { false } }

/** Executes a shell command as root and returns stdout */
fun execRoot(cmd: String): String {
    val fullCmd = arrayOf("su", "-c", cmd)
    return try {
        val proc = Runtime.getRuntime().exec(fullCmd)
        val reader = BufferedReader(InputStreamReader(proc.inputStream))
        val output = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            output.append(line).append("\n")
            line = reader.readLine()
        }
        proc.waitFor()
        output.toString().trim()
    } catch (e: Exception) {
        throw RuntimeException("Shell exec failed: ${e.message}")
    }
}

/** Executes root command, returns Pair(stdout, stderr) */
fun execRootWithError(cmd: String): Pair<String, String> {
    val fullCmd = arrayOf("su", "-c", cmd)
    return try {
        val proc = Runtime.getRuntime().exec(fullCmd)
        val outReader = BufferedReader(InputStreamReader(proc.inputStream))
        val errReader = BufferedReader(InputStreamReader(proc.errorStream))
        val out = StringBuilder(); var line = outReader.readLine()
        while (line != null) { out.append(line).append("\n"); line = outReader.readLine() }
        val err = StringBuilder(); line = errReader.readLine()
        while (line != null) { err.append(line).append("\n"); line = errReader.readLine() }
        proc.waitFor()
        Pair(out.toString().trim(), err.toString().trim())
    } catch (e: Exception) {
        throw RuntimeException("Shell exec failed: ${e.message}")
    }
}

}

