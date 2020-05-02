package es.lnsd.invoke.ui.toolwindow

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.tree.TreeNode

interface InvokeToolWindowContract {

    interface View {
        fun updateTreeModel(root: TreeNode)
        fun registerToolbarActions(vararg actions: AnAction)
    }

    interface ViewListener {
        fun onUpdateTasksActionPerformed(e: AnActionEvent)
    }
}