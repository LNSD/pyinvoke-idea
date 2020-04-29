package es.lnsd.invoke.ui.toolwindow

import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.jetbrains.python.statistics.modules

class InvokeToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.title = "invoke"
        toolWindow.isAutoHide = true

        DumbService.getInstance(project).runWhenSmart {
            // val files = MakefileTargetIndex.allTargets(project).filterNot { it.isSpecialTarget || it.isPatternTarget }.groupBy {
            //     it.containingFile
            // }.map {
            //     MakefileFileNode(it.key, it.value.map(::MakefileTargetNode))
            // }

            val modules = project.modules.map { module ->
                ModuleNode(module, emptyList())
            }
            val root = InvokeRootNode(modules)
            val panel = InvokeToolWindow(root)
            toolWindow.component.add(panel)
        }
    }
}
