package es.lnsd.invoke.ui.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.treeStructure.Tree
import es.lnsd.invoke.ui.toolwindow.actions.UpdateTasksAction
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreeSelectionModel

class InvokeToolWindow(treeRoot: TreeNode): SimpleToolWindowPanel(true){

    private val tree = Tree(DefaultTreeModel(treeRoot))

    init {
        tree.apply {
            isRootVisible = false
            cellRenderer = InvokeTreeCellRenderer()
            selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        }
        add(ScrollPaneFactory.createScrollPane(tree))

        val toolBarPanel = JPanel(GridLayout())
        val actionGroup = DefaultActionGroup()
        val updateTasksAction = UpdateTasksAction()
        // updateTasksAction.apply {
        //     registerCustomShortcutSet(CustomShortcutSet(KeyEvent.VK_F5), this)
        // }
        actionGroup.add(updateTasksAction)

        toolBarPanel.add(
            ActionManager.getInstance().createActionToolbar("InvokeToolWindowToolbar", actionGroup, true).component
        )
        toolbar = toolBarPanel
    }

    fun update(treeRoot: TreeNode) {
        val model = tree.model as DefaultTreeModel
        model.setRoot(treeRoot)
        model.reload()
    }
}