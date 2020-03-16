@file:CompilerOptions("-jvm-target", "1.8")
@file:DependsOn("org.jetbrains.kotlin:kotlin-reflect:1.3.70")
@file:DependsOn("com.samskivert:jmustache:1.15")
@file:Import(
    "templates/index.kts",
    "templates/projects.kts"
)

import com.samskivert.mustache.Mustache
import java.io.File
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

val templateRoot = Path.of("templates")
val outputRoot = Path.of("build")

fun render(
    template: String,
    context: Any,
    outName: String = template,
    templateMap: Map<String, Any> = emptyMap()
): String {
    println("Rendering template $template for $outName")
    val templatePath = templateRoot.resolve("$template.mustache")
    val compiler = Mustache.compiler()
        .withLoader { partial ->
            templateMap[partial]?.let {
                when (it) {
                    is Path -> Files.newBufferedReader(it)
                    is File -> it.bufferedReader()
                    is String -> StringReader(it)
                    else -> {
                        System.err.println("Unknown template partial value type ${it::class} for $partial; falling back to \"\"")
                        StringReader("")
                    }
                }
            } ?: run {
                System.err.println("Unresolved template partial $partial; falling back to \"\"")
                StringReader("")
            }
        }

    val output = compiler.compile(Files.newBufferedReader(templatePath)).execute(context)
    Files.writeString(
        outputRoot.resolve("$outName.html"), output,
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
    )

    return output
}

val navbarItems = listOf(
    object {
        val href = "/"
        val title = "home"
    },
    object {
        val href = "/projects.html"
        val title = "projects"
    }
)

render(
    "base", INDEX_TEMPLATE + ("navbar_items" to navbarItems), "index", mapOf(
        "main" to templateRoot.resolve("index.mustache"),
        "head" to """
            <link rel="stylesheet" href="/static/css/index.css">
            <script defer="" src="/static/js/cycle.js"></script>
            <script defer="" src="/static/js/index.js"></script>
        """.trimIndent()
    )
)

render(
    "base", PROJECTS_TEMPLATE + ("navbar_items" to navbarItems), "projects", mapOf(
        "main" to templateRoot.resolve("projects.mustache"),
        "head" to """
            <link rel="stylesheet" href="/static/css/project.css">
        """.trimIndent()
    )
)

println("Done")
