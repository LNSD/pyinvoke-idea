package es.lnsd.invoke.ui.toolwindow.tree

import es.lnsd.invoke.InvokeRoundBlackIcon
import javax.swing.Icon
import javax.swing.tree.TreeNode

abstract class InvokeTreeNode(open val name: String?): TreeNode {
    open val icon: Icon = InvokeRoundBlackIcon
    open val help: String? = null
    open val default: String? = null
}
