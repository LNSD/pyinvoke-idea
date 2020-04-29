package es.lnsd.invoke.ui.toolwindow.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.jetbrains.python.statistics.modules
import es.lnsd.invoke.parseTasks
import es.lnsd.invoke.runInvoke
import es.lnsd.invoke.ui.toolwindow.InvokeRootNode
import es.lnsd.invoke.ui.toolwindow.InvokeToolWindow
import es.lnsd.invoke.ui.toolwindow.ModuleNode
import es.lnsd.invoke.ui.toolwindow.TaskNode

class UpdateTasksAction: AnAction("Update", "Update tasks", AllIcons.Actions.Refresh) {

    override fun actionPerformed(e: AnActionEvent) {
        ApplicationManager.getApplication().executeOnPooledThread {
            val window: ToolWindow? = e.project?.let { ToolWindowManager.getInstance(it).getToolWindow("Invoke") }
            val panel = (window?.component?.components?.first() as InvokeToolWindow)

            val modules = e.project!!.modules.map { mod ->
                val out = runInvoke(mod, "--list", "--list-format=json")
                val tasks = parseTasks(out).tasks
                ModuleNode(mod, tasks.map(::TaskNode))
            }
            val root = InvokeRootNode(modules)

            panel.update(root)
        }
    }
}