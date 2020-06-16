@file:CompilerOptions("-jvm-target", "1.8")

@file:DependsOn("org.jetbrains.kotlin:kotlin-reflect:1.3.70")
@file:DependsOn("com.samskivert:jmustache:1.15")
@file:DependsOn("com.github.rjeschke:txtmark:0.13")
@file:DependsOn("com.googlecode.htmlcompressor:htmlcompressor:1.5.2")

@file:Import(
    "templates/index.kts",
    "templates/overview.kts",
    "templates/projects.kts"
)

import com.samskivert.mustache.Mustache
import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import com.github.rjeschke.txtmark.*
import java.io.File
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val templateRoot = Path.of("templates")
val outputRoot = Path.of("build")

val compressor = HtmlCompressor().apply {
    setRemoveIntertagSpaces(true)
}

val processor: (String) -> String = {
    Processor.process(it, Configuration.DEFAULT)
}

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

    val output =
        compressor.compress(
            compiler.compile(
                Files.newBufferedReader(templatePath)
            ).execute(context)
        )

    val outPath = when (outName) {
        "index" -> outputRoot.resolve("index.html")
        else -> outputRoot.resolve("$outName/index.html")
    }

    Files.createDirectories(outPath.parent)

    Files.writeString(
        outPath, output,
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
    )

    return output
}

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss.SSS O")
val mixins = mapOf(
    "age" to LocalDate.of(2002, Month.JULY, 4)
            .until(LocalDate.now(ZoneOffset.ofHours(-4)))
            .years,
    "timestamp" to formatter.format(Instant.now().atZone(ZoneOffset.UTC))
)

render(
    "base", index(processor) + mixins, "index", mapOf(
        "main" to templateRoot.resolve("index.mustache"),
        "head" to """
            <link rel="stylesheet" href="/static/css/base.css">
        """.trimIndent()
    )
)

render(
    "base", overview(processor) + mixins, "overview", mapOf(
        "main" to templateRoot.resolve("overview.mustache"),
        "head" to """
            <link rel="stylesheet" href="/static/css/base.css">
            <link rel="stylesheet" href="/static/css/overview.css">
        """.trimIndent()
    )
)

render(
    "base", projects(processor) + mixins, "projects", mapOf(
        "main" to templateRoot.resolve("projects.mustache"),
        "head" to """
            <link rel="stylesheet" href="/static/css/base.css">
            <link rel="stylesheet" href="/static/css/projects.css">
        """.trimIndent()
    )
)

render(
    "resume", mixins, "resume"
)

println("Done")
