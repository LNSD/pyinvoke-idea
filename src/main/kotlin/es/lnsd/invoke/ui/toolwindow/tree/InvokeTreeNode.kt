package es.lnsd.invoke.ui.toolwindow.tree

import javax.swing.Icon
import javax.swing.tree.TreeNode

abstract class InvokeTreeNode(val name: String) : TreeNode {
    abstract val icon: Icon
}
