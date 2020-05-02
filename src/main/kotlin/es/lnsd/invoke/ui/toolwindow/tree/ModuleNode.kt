package es.lnsd.invoke.ui.toolwindow.tree

import com.intellij.openapi.module.Module
import es.lnsd.invoke.InvokeToolWindowIcon
import java.util.Collections.enumeration
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

class ModuleNode(module: Module, private val nodes: List<TaskNode>) : InvokeTreeNode(module.name) {

    init {
        nodes.forEach { it.parent = this }
    }

    internal lateinit var parent: ModulesRootNode

    override val icon: Icon
        get() = InvokeToolWindowIcon

    override fun children(): Enumeration<out TreeNode>? = enumeration(nodes)

    override fun isLeaf() = false

    override fun getChildCount() = nodes.size

    override fun getParent() = parent

    override fun getChildAt(i: Int) = nodes[i]

    override fun getIndex(node: TreeNode) = nodes.indexOf(node)

    override fun getAllowsChildren() = true
}