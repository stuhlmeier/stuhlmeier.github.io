import java.nio.file.Path
import kotlin.math.roundToInt

data class Skill(
    private val _proficiency: Double,
    private val _name: String,
    private val _qualifier: String? = null,
    private val _iconPath: String? = null
) {
    val proficiency get() = _proficiency
    val name get() = _name
    val qualifier get() = _qualifier

    val icon_path get() = Path.of("/static/image/logo/", _iconPath ?: "${_name.toLowerCase().trim()}.svg")
    val meter_percentage get() = "${(_proficiency * 20).roundToInt()}%"
    val meter_class
        get() = when {
            _proficiency >= 3.5 -> "meter-good"
            _proficiency >= 2 -> "meter-ok"
            else -> "meter-bad"
        }

    val meter_style get() = "width: ${meter_percentage}"
}

data class SkillCategory(
    private val _name: String,
    private val _skills: List<Skill>,
    private val _text: String = ""
) {
    val name get() = _name
    val skills get() = _skills
    val description get() = _text
}

val INDEX_SKILL_CATEGORIES = listOf(
    SkillCategory(
        "JVM", listOf(
            Skill(5.00, "Java", "14"),
            Skill(5.00, "Kotlin", "1.3"),
            Skill(3.50, "Spring", "5"),
            Skill(4.25, "Gradle", "6"),
            Skill(3.50, "Maven", "3", "apache.svg")
        ),
        _text = """
            I have most experience with the JVM ecosystem; Kotlin and Gradle are tools I use almost daily.
        """.trimIndent()
    ),
    SkillCategory(
        "Native", listOf(
            Skill(4.75, "C++", "20", "cpp.svg"),
            Skill(3.50, "C", "18"),
            Skill(1.75, "Rust"),
            Skill(3.75, "CMake", "3.14+"),
            Skill(3.50, "Meson", _iconPath = "meson.png")
        ),
        _text = """
            I have used C++ for a while, beginning with various competitive programming usages and moving on
            to proper native development. While I primarily develop on Linux, I tend to prefer
            <abbr title="or, at least, to the extent possible thanks to tooling and ABI incompatibilities &#x1F643;">cross-platform</abbr>
            build tools such as CMake and Meson.
        """.trimIndent()
    ),
    SkillCategory(
        ".NET", listOf(
            Skill(5.00, "C#", "8", "cs.svg"),
            Skill(3.75, "ASP.NET", "Core 3", "net.svg")
        ),
        _text = """
            There isn't much to say here; ASP.NET is simply the framework I have used the most.
        """.trimIndent()
    ),
    SkillCategory(
        "Tools", listOf(
            Skill(4.50, "Git")
        )
    ),
    SkillCategory(
        "Other", listOf(
            Skill(4.75, "JavaScript", "ES10", "js.svg"),
            Skill(4.75, "CSS", "3", "css3.svg"),
            Skill(4.25, "Python", "3"),
            Skill(4.00, "TypeScript", "3", "ts.svg")
        ),
        _text = """
            This site itself was written from scratch with JavaScript, CSS and Kotlin,
            using Mustache as a templating engine.
        """.trimIndent()
    )
)

val INDEX_TEMPLATE = mapOf(
    "title" to null,
    "index" to true,
    "categories" to INDEX_SKILL_CATEGORIES
)
