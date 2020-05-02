package es.lnsd.invoke.ui.toolwindow.tree

import es.lnsd.invoke.InvokeToolWindowIcon
import java.util.Collections.enumeration
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

class ModulesRootNode(private val nodes: List<ModuleNode>): InvokeTreeNode("invoke") {

    init {
        nodes.forEach { it.parent = this }
    }

    override val icon: Icon
        get() = InvokeToolWindowIcon

    override fun children(): Enumeration<out TreeNode> = enumeration(nodes)

    override fun isLeaf() = false

    override fun getChildCount() = nodes.size

    override fun getParent() = null

    override fun getChildAt(i: Int) = nodes[i]

    override fun getIndex(node: TreeNode) = nodes.indexOf(node)

    override fun getAllowsChildren() = true
}