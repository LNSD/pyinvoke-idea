package es.lnsd.invoke.ui.toolwindow.tree

import es.lnsd.invoke.InvokeRoundBlackIcon
import es.lnsd.invoke.TasksCollection
import javax.swing.Icon

class ModuleNode(moduleName: String, collection: TasksCollection): CollectionNode(collection) {
    override val name: String = moduleName
    override val icon: Icon = InvokeRoundBlackIcon
}