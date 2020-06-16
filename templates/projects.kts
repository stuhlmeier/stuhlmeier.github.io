import java.nio.file.Path

data class Tag(
    private val _text: String,
    private val _class: String = "",
    private val _description: String? = null,
    private val _iconPaths: List<String>? = null
) {
    val tag get() = _text
    val tag_description get() = _description
    val tag_class get() = _class
    val tag_icon_paths get() = _iconPaths?.map { Path.of("/static/image/logo", it) }
}

data class Project(
    private val _name: String,
    private val _id: String,
    private val _brief: String?,
    private val _description: String?,
    private val _iconPath: String,
    private val _url: String? = null,
    private val _tags: List<Tag>,

    private val processor: (String) -> String = { it }
) {
    val url get() = _url
    val name get() = _name
    val id get() = _id
    val icon_path get() = Path.of("/static/image/logo/", _iconPath)
    val tags get() = _tags
    val brief get() = _brief
    val text
        get() = processor(buildString {
            _brief?.let { append("<span class=\"brief\">$it</span> ") }
            _description?.let(::append)
        })
}

val PROJECTS_TAG_MIT = Tag(
    "mit", "info", "MIT License",
    listOf("mit.svg")
)
val PROJECTS_TAG_CC_BY_SA = Tag(
    "cc-by-sa", "info", "CC BY-SA",
    listOf("cc.svg", "cc-by.svg", "cc-sa.svg")
)
val PROJECTS_TAG_GPL = Tag(
    "gpl", "info", "GNU General Public License",
    listOf("gnu.svg")
)

val PROJECTS_TAG_UNRELEASED = Tag("unreleased", "warn", "This project is in a very early stage of development")
val PROJECTS_TAG_EXPERIMENT = Tag("experiment", "warn", "This project is not intended for production use")

val PROJECTS_TAG_LIBRARY = Tag("library")
val PROJECTS_TAG_TOOL = Tag("tool")
val PROJECTS_TAG_TEXT = Tag("text")

val PROJECTS_TAG_KOTLIN = Tag("kotlin")
val PROJECTS_TAG_CPP = Tag("c++")
val PROJECTS_TAG_JAVASCRIPT = Tag("javascript")
val PROJECTS_TAG_ASCIIDOC = Tag("asciidoc")

val PROJECTS_TAG_LLVM = Tag("llvm")
val PROJECTS_TAG_CLANG = Tag("clang")
val PROJECTS_TAG_OPENCV = Tag("opencv")

val PROJECTS_PROJECTS = listOf(
    Project(
        "Kotlin events", "kotlin-events",
        """
            <code>kotlinx.events</code> is a small, simple event library for Kotlin,
            focused on ease of use and familiarity.
        """.trimIndent(), """
            It provides a high-level façade to abstract away the details of handling events and event listeners,
            analogously to the <code>event</code> language feature in C#.
        """.trimIndent(),
        "kotlin.svg",
        "https://github.com/stuhlmeier/kotlin-events/",
        _tags = listOf(
            PROJECTS_TAG_MIT,
            PROJECTS_TAG_KOTLIN,
            PROJECTS_TAG_LIBRARY
        )
    ),
    Project(
        "memistry", "memistry",
        """
            A web-based chemistry trainer,
        """.trimIndent(), """
            written mostly in a day for self-study purposes.

            memistry provides randomized questions to aid learning or memorization;
            currently, it generates questions concerning the periodic table, its elements, and their properties.
        """.trimIndent(),
        "js.svg",
        "https://stuhlmeier.github.io/memistry/",
        _tags = listOf(
            PROJECTS_TAG_GPL,
            PROJECTS_TAG_JAVASCRIPT,
            PROJECTS_TAG_TOOL
        )
    ),
    Project(
        "GDPR", "gdpr",
        """
            A summary of the General Data Protection Regulation
            (<em>Regulation (EU) 2016/679 of the European Parliament and of the Council of 27 April 2016</em>).
        """.trimIndent(), """
            Condenses the 90 pages of the GDPR into about 40 pages,
            with fully linked references and less legalese to sift through.
        """.trimIndent(),
        "asciidoctor.svg",
        "https://github.com/stuhlmeier/gdpr/",
        _tags = listOf(
            PROJECTS_TAG_CC_BY_SA,
            PROJECTS_TAG_ASCIIDOC,
            PROJECTS_TAG_TEXT
        )
    ),
    Project(
        "Melange", "melange",
        "A documentation generator for the Clang frontend.", """
            Written using LLVM 9/10 LibTooling and generates HTML5 documentation using Mustache.

            <span class="warn">This project has not been finished and is not currently public.</span>
        """.trimIndent(),
        "cpp.svg",
        "https://github.com/stuhlmeier/melange/",
        _tags = listOf(
            PROJECTS_TAG_GPL,
            PROJECTS_TAG_UNRELEASED,
            PROJECTS_TAG_CPP,
            PROJECTS_TAG_LLVM,
            PROJECTS_TAG_CLANG,
            PROJECTS_TAG_LIBRARY,
            PROJECTS_TAG_TOOL
        )
    ),
    Project(
        "ringcode", "ringcode",
        "A circular, color-based tag format for encoding bytes.", """
            Consists of a generator and a reader written in JavaScript
            with the Canvas API and OpenCV's Emscripten bindings.
            This was conceived and written as part of a school project
            involving augmented reality and Microsoft's HoloLens&trade;.

            While it is not particularly efficient with respect to storage density,
            it functioned primarily as a learning experience,
            and as a proof-of-concept for the cross-platform usage of OpenCV.
        """.trimIndent(),
        "js.svg",
        "https://github.com/stuhlmeier/ringcode-js/",
        _tags = listOf(
            PROJECTS_TAG_GPL,
            PROJECTS_TAG_EXPERIMENT,
            PROJECTS_TAG_JAVASCRIPT,
            PROJECTS_TAG_OPENCV,
            PROJECTS_TAG_LIBRARY,
            PROJECTS_TAG_TOOL
        )
    )
)

fun projects(processor: (String) -> String): Map<String, Any?> {
    return mapOf(
        "title" to "projects",
        "projects" to PROJECTS_PROJECTS.map { it.copy(processor = processor) }
    )
}
