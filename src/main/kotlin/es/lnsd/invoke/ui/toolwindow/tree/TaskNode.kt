package es.lnsd.invoke.ui.toolwindow.tree

import es.lnsd.invoke.InvokeToolWindowTaskIcon
import es.lnsd.invoke.Task
import es.lnsd.invoke.ui.toolwindow.tree.InvokeTreeNode
import es.lnsd.invoke.ui.toolwindow.tree.ModuleNode
import java.util.Collections.emptyEnumeration
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

class TaskNode(task: Task): InvokeTreeNode(task.name) {

    override val icon: Icon
        get() = InvokeToolWindowTaskIcon

    internal lateinit var parent: ModuleNode

    override fun children(): Enumeration<out TreeNode>? = emptyEnumeration<TreeNode>()

    override fun isLeaf() = true

    override fun getChildCount() = 0

    override fun getParent() = parent

    override fun getChildAt(i: Int) = null

    override fun getIndex(node: TreeNode) = 0

    override fun getAllowsChildren() = false
}