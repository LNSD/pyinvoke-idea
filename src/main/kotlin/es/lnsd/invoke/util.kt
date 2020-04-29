package es.lnsd.invoke

import com.intellij.openapi.module.Module
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.jetbrains.python.PyNames
import com.jetbrains.python.psi.PyFile
import com.jetbrains.python.psi.PyUtil

const val INVOKE_TASKS_PYFILE : String = "tasks.py"
const val INVOKE_TASKS_PYMOD : String = "tasks"

fun findTasksPy(module: Module): PyFile? {
    for (root in PyUtil.getSourceRoots(module)) {
        val child = root.findChild(INVOKE_TASKS_PYFILE)
        if (child != null) {
            val file = PsiManager.getInstance(module.project).findFile(child)
            if (file is PyFile) {
                return file
            }
        }
    }
    return null
}

fun hasTasksPy(module: Module) = findTasksPy(module) != null

fun findTasksPyModule(module: Module): PsiDirectory? {
    for (root in PyUtil.getSourceRoots(module)) {
        val child = root.findChild(INVOKE_TASKS_PYMOD)
        if (child != null) {
            val directory = PsiManager.getInstance(module.project).findDirectory(child)
            if (directory is PsiDirectory && directory.findFile(PyNames.INIT_DOT_PY) != null) {
                return directory
            }
        }
    }
    return null
}

fun hasTasksPyModule(module: Module) = findTasksPyModule(module) != null

fun isInvokeModule(module: Module) = hasTasksPy(module) xor hasTasksPyModule(module)
