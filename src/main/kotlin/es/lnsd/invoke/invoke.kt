package es.lnsd.invoke

import com.intellij.execution.RunCanceledByUserException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.PathEnvironmentVariableUtil
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.module.Module
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.util.SystemInfo
import com.intellij.util.EnvironmentUtil
import com.jetbrains.python.packaging.IndicatedProcessOutputListener
import com.jetbrains.python.packaging.PyExecutionException
import com.jetbrains.python.sdk.PythonSdkType
import com.jetbrains.python.sdk.basePath
import com.jetbrains.python.sdk.pythonSdk
import org.jetbrains.annotations.SystemDependent

const val INVOKE_COMMAND : String = "invoke"
const val INVOKE_COMMAND_SHORT : String = "inv"

/**
 * Runs invoke for the specified Project module.
 */
fun runInvoke(module: Module, vararg args: String): String {
    val workDirectory = module.basePath!!
    val sdk = module.pythonSdk!!
    return runInvoke(sdk, workDirectory, *args)
}

fun GeneralCommandLine.findExecutableInPath(): String? {
    val executable = exePath
    if ("/" in executable || "\\" in executable) return executable
    val paths = listOfNotNull(effectiveEnvironment["PATH"], System.getenv("PATH"), EnvironmentUtil.getValue("PATH"))
    return paths
        .asSequence()
        .mapNotNull { path ->
            if (SystemInfo.isWindows) {
                PathEnvironmentVariableUtil.getWindowsExecutableFileExtensions()
                    .mapNotNull { ext -> PathEnvironmentVariableUtil.findInPath("$executable$ext", path, null)?.path }
                    .firstOrNull()
            }
            else {
                PathEnvironmentVariableUtil.findInPath(executable, path, null)?.path
            }
        }
        .firstOrNull()
}

/**
 * Runs invoke for the specified project path.
 */
fun runInvoke(sdk: Sdk, workDir: @SystemDependent String, vararg args: String): String {

    val command = listOf(INVOKE_COMMAND) + args
    val commandLine = GeneralCommandLine(command).withWorkDirectory(workDir)

    // Patch
    PythonSdkType.patchCommandLineForVirtualenv(commandLine, sdk)
    commandLine.findExecutableInPath()?.let {
        commandLine.exePath = it
    }

    val handler = CapturingProcessHandler(commandLine)
    val indicator = ProgressManager.getInstance().progressIndicator

    val result = with(handler) {
        when {
            indicator != null -> {
                addProcessListener(IndicatedProcessOutputListener(indicator))
                runProcessWithProgressIndicator(indicator)
            }
            else ->
                runProcess()
        }
    }

    return with(result) {
        when {
            isCancelled ->
                throw RunCanceledByUserException()
            exitCode != 0 ->
                throw PyExecutionException("Error Running Invoke", INVOKE_COMMAND, args.asList(),
                    stdout, stderr, exitCode, emptyList())
            else -> stdout
        }
    }
}
