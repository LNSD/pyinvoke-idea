package es.lnsd.invoke.ui.toolwindow

import com.intellij.openapi.module.Module
import es.lnsd.invoke.InvokeToolWindowIcon
import java.util.Collections.enumeration
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

class ModuleNode(module: Module, private val tasks: List<TaskNode>) : InvokeTreeNode(module.name) {
    init {
        for (task in tasks) {
            task.parent = this
        }
    }

    internal lateinit var parent: InvokeRootNode

    override val icon: Icon
        get() = InvokeToolWindowIcon

    override fun children(): Enumeration<out TreeNode>? = enumeration(tasks)

    override fun isLeaf() = false

    override fun getChildCount() = tasks.size

    override fun getParent() = parent

    override fun getChildAt(i: Int) = tasks[i]

    override fun getIndex(node: TreeNode) = tasks.indexOf(node)

    override fun getAllowsChildren() = true
}