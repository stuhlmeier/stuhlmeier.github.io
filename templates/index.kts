import java.nio.file.Path
import kotlin.math.roundToInt

data class Skill(
    private val _proficiency: Double,
    private val _name: String,
    private val _qualifier: String? = null,
    private val _iconPath: String? = null,

    private val processor: (String) -> String = { it }
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
    private val _text: String = "",

    private val processor: (String) -> String = { it }
) {
    val name get() = _name
    val skills get() = _skills
    val description get() = processor(_text)

    fun withProcessor(processor: (String) -> String): SkillCategory {
        return copy(_skills = _skills.map { it.copy(processor = processor) }, processor = processor)
    }
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
Most of my programming experience has been made on the JVM.
Kotlin and Gradle are tools I use on an almost-daily basis;
in terms of tooling, the JVM ecosystem is the software environment I feel most comfortable developing for.

I have been writing Java since 2014, shortly before the release of Java 8.
I started primarily using Kotlin when it was first released as version 1.0 back in 2016,
and have since familiarized myself with all aspects of the language.

I like to write just about anything for the JVM, especially cloud services and user interfaces. 
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
I have used C++ for a while. I started in 2016 with competitive programming in mind,
though I thankfully stayed away from the various infamous bad practices associated with said field
(I'm looking at you, `using namespace std` and `bits/stdc++.h`) and have since become quite experienced with
modern C++ practices.

While I work on Linux as much as possible, I tend to prefer
<abbr title="or, at least, to the extent possible thanks to tooling and ABI incompatibilities &#x1F643;">cross-platform</abbr>
build tools such as Meson and CMake.

Additionally, I have written a few OpenGL applications from scratch for fun.

I like to use C++ and Rust for systems programming and for writing low-level, high-performance tools.
When writing C++, I prefer using Meson and Clang if they are available.
        """.trimIndent()
    ),
    SkillCategory(
        ".NET", listOf(
            Skill(5.00, "C#", "8", "cs.svg"),
            Skill(4.25, "MonoGame", "3", "monogame.png"),
            Skill(3.75, "ASP.NET", "Core 3", "net.svg")
        ),
        _text = """
C# has become increasingly more appealing over the years, especially with the release of .NET Core
and the solidification of the .NET Standard. While it was easy to start out with due to my prior Java knowledge,
my work experience at a sizeable software company, in addition to my work on various personal projects,
have enabled me to fully grasp proper C# idioms.

I like to use C# to write cloud-based web applications (thanks, Azure) and, occasionally, video games.
I usually prefer MonoGame over Unity and Godot as I find the <abbr title="bring your own engine">BYOE</abbr> approach
to be much more flexible and extensible, but I am still open to using other options.
        """.trimIndent()
    ),
    SkillCategory(
        "Tools", listOf(
            Skill(4.75, "GNU Coreutils", _iconPath = "gnu.svg"),
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
This site itself was
<a href="https://github.com/stuhlmeier/stuhlmeier.github.io">written from scratch with JavaScript, CSS and Kotlin</a>,
using Kotlin scripts with Mustache and Markdown as a templating system.
        """.trimIndent()
    )
)

fun index(processor: (String) -> String): Map<String, Any?> {
    return mapOf(
        "title" to null,
        "index" to true,
        "categories" to INDEX_SKILL_CATEGORIES.map { it.withProcessor(processor) }
    )
}
