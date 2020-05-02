package es.lnsd.invoke.ui.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.treeStructure.Tree
import es.lnsd.invoke.ui.toolwindow.tree.InvokeTreeCellRenderer
import es.lnsd.invoke.ui.toolwindow.tree.ModulesRootNode
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreeSelectionModel

class InvokeToolWindow(toolWindow: ToolWindow): InvokeToolWindowContract.View {

    val panel = SimpleToolWindowPanel(true)

    private val toolbarPanel = JPanel(GridLayout())
    private val tree = Tree(DefaultTreeModel(ModulesRootNode(emptyList())))
    private val scrollPane = ScrollPaneFactory.createScrollPane(tree)
    private val toolbarActionGroup = DefaultActionGroup()

    init {
        tree.apply {
            isRootVisible = false
            cellRenderer = InvokeTreeCellRenderer()
            selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        }
        panel.add(scrollPane)

        toolbarPanel.add(
            ActionManager.getInstance().createActionToolbar("InvokeToolWindowToolbar", toolbarActionGroup, true).component
        )
        panel.toolbar = toolbarPanel
        toolWindow.component.add(panel)
    }

    override fun updateTreeModel(root: TreeNode) {
        val treeModel = tree.model as DefaultTreeModel
        treeModel.setRoot(root)
        treeModel.reload()
    }

    override fun registerToolbarActions(vararg actions: AnAction) {
        actions.forEach { action -> toolbarActionGroup.add(action) }
    }
}