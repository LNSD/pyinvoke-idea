package es.lnsd.invoke

import com.google.gson.Gson


data class InvokeTasks(
    @JvmField val name: String,
    @JvmField val help: String,
    @JvmField val default: String,
    @JvmField val tasks: List<Task>,
    @JvmField val collections: List<InvokeTasks>
)

data class Task(
    @JvmField val name: String,
    @JvmField val help: String,
    @JvmField val aliases: List<String>
)

private val gson by lazy { Gson() }

fun parseTasks(tasks: String): InvokeTasks = gson.fromJson(tasks, InvokeTasks::class.java)