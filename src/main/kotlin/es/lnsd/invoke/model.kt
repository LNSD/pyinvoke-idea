package es.lnsd.invoke

import com.google.gson.Gson


data class TasksCollection(
    @JvmField val name: String?,
    @JvmField val help: String?,
    @JvmField val default: String,
    @JvmField val tasks: List<Task>,
    @JvmField val collections: List<TasksCollection>
)

data class Task(
    @JvmField val name: String,
    @JvmField val help: String,
    @JvmField val aliases: List<String>
)

private val gson by lazy { Gson() }

fun parseTasks(tasks: String): TasksCollection = gson.fromJson(tasks, TasksCollection::class.java)