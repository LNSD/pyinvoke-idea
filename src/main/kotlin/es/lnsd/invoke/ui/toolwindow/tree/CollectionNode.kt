package es.lnsd.invoke.ui.toolwindow.tree

import es.lnsd.invoke.InvokeRoundBlackIcon
import es.lnsd.invoke.TasksCollection
import java.util.Collections.enumeration
import java.util.Enumeration
import javax.swing.Icon
import javax.swing.tree.TreeNode

open class CollectionNode(collection: TasksCollection): InvokeTreeNode(collection.name) {

    override val icon: Icon = InvokeRoundBlackIcon
    override val help: String? = collection.help
    override val default: String? = collection.default

    private var nodes: List<InvokeTreeNode>

    init {
        nodes = collection.collections.filter { it.name != null }.map(::CollectionNode).onEach { it.parent = this } +
            collection.tasks.map(::TaskNode).onEach { it.parent = this }
    }

    lateinit var parent: InvokeTreeNode

    override fun children(): Enumeration<out TreeNode> = enumeration(nodes)

    override fun isLeaf(): Boolean = false

    override fun getChildCount(): Int = nodes.size

    override fun getParent(): TreeNode = parent

    override fun getChildAt(i: Int): InvokeTreeNode = nodes[i]

    override fun getIndex(node: TreeNode): Int = nodes.indexOf(node)

    override fun getAllowsChildren(): Boolean = true
}