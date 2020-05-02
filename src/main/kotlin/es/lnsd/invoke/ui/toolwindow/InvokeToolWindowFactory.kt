package es.lnsd.invoke.ui.toolwindow

import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class InvokeToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.title = "invoke"
        toolWindow.isAutoHide = true

        DumbService.getInstance(project).runWhenSmart {
            val view = InvokeToolWindow(toolWindow)
            val presenter = InvokeToolWindowPresenter(view, project)
            presenter.setContent()
        }
    }
}
