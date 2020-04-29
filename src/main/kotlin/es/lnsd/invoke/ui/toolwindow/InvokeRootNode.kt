package es.lnsd.invoke.ui.toolwindow

import es.lnsd.invoke.InvokeToolWindowIcon
import java.util.Collections.enumeration
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

class InvokeRootNode(private val modules: List<ModuleNode>) : InvokeTreeNode("invoke") {
    init {
        for (module in modules) {
            module.parent = this
        }
    }

    override val icon: Icon
        get() = InvokeToolWindowIcon

    override fun children(): Enumeration<out TreeNode> = enumeration(modules)

    override fun isLeaf() = false

    override fun getChildCount() = modules.size

    override fun getParent() = null

    override fun getChildAt(i: Int) = modules[i]

    override fun getIndex(node: TreeNode) = modules.indexOf(node)

    override fun getAllowsChildren() = true
}