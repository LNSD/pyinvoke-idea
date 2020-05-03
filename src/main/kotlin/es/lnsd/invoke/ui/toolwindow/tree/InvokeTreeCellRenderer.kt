package es.lnsd.invoke.ui.toolwindow.tree

import com.intellij.ui.ColoredTreeCellRenderer
import javax.swing.JTree

class InvokeTreeCellRenderer : ColoredTreeCellRenderer() {
    override fun customizeCellRenderer(tree: JTree, value: Any, selected: Boolean, expanded: Boolean, leaf: Boolean, row: Int, hasFocus: Boolean) {
        value as InvokeTreeNode
        icon = value.icon
        append(value.name ?: "null")
    }
}