import es.lnsd.invoke.parseTasks
import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun testJsonTasksParsing() {
        val TASKS_TREE_JSON: String = readFile("tree.json")
        val tasks = parseTasks(TASKS_TREE_JSON)
        println("PASS")
    }

    private fun readFile(path: String): String {
        return String(javaClass.getResource(path).readBytes())
    }
}
