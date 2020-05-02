package es.lnsd.invoke.ui.toolwindow

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CustomShortcutSet
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.jetbrains.python.statistics.modules
import es.lnsd.invoke.parseTasks
import es.lnsd.invoke.runInvoke
import es.lnsd.invoke.ui.toolwindow.tree.ModuleNode
import es.lnsd.invoke.ui.toolwindow.tree.ModulesRootNode
import es.lnsd.invoke.ui.toolwindow.tree.TaskNode
import java.awt.event.KeyEvent

class InvokeToolWindowPresenter(private val view: InvokeToolWindow, private val project: Project):
    InvokeToolWindowContract.ViewListener {

    init {
        val updateTasksAction: AnAction = object: AnAction("Update", "Update tasks", AllIcons.Actions.Refresh) {
            init { registerCustomShortcutSet(CustomShortcutSet(KeyEvent.VK_F5), view.panel) }
            override fun actionPerformed(e: AnActionEvent) = onUpdateTasksActionPerformed(e)
        }
        view.registerToolbarActions(updateTasksAction)
    }

    fun setContent() {
        ApplicationManager.getApplication().executeOnPooledThread {
            val modules = project.modules.map { mod ->
                val out = runInvoke(mod, "--list", "--list-format=json")
                val tasks = parseTasks(out).tasks
                ModuleNode(mod, tasks.map(::TaskNode))
            }
            view.updateTreeModel(ModulesRootNode(modules))
        }
    }

    override fun onUpdateTasksActionPerformed(e: AnActionEvent) = setContent()
}