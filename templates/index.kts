import java.nio.file.Path
import kotlin.math.roundToInt

data class Skill(
    private val _proficiency: Double,
    private val _name: String,
    private val _qualifier: String? = null,
    private val _iconPath: String? = null,
    private val _url: String? = null,
    private val _richTooltipText: String? = null,

    private val processor: (String) -> String = { it }
) {
    val is_phony get() = _proficiency.isNaN()
    val proficiency get() = _proficiency

    val name get() = _name
    val qualifier get() = _qualifier

    val url get() = _url

    val icon_path get() = Path.of("/static/image/logo/", _iconPath ?: "${_name.toLowerCase().trim()}.svg")
    val meter_percentage get() = "${(_proficiency * 20).roundToInt()}%"
    val meter_class
        get() = when {
            _proficiency >= 3.5 -> "meter-good"
            _proficiency >= 2 -> "meter-ok"
            else -> "meter-bad"
        }

    val meter_style get() = "width: ${meter_percentage}"

    val tooltip_text get() =
        if (is_phony) name
        else "$name: $_proficiency/5.0"

    val rich_tooltip_text get() = (_richTooltipText?.plus(": $_proficiency/5.0")) ?: tooltip_text
}

data class SkillCategory(
    private val _name: String,
    private val _skills: List<Skill>,
    private val _text: String = "",
    private val _small: Boolean = false,

    private val processor: (String) -> String = { it }
) {
    val name get() = _name
    val skills get() = _skills
    val description get() = processor(_text)
    val small get() = _small

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
though I thankfully stayed away from the various infamous bad practices associated with this field
(I'm looking at you, `using namespace std` and `bits/stdc++.h`) and have since become quite experienced with
modern C++ practices.

While I work on Linux as much as possible, I tend to prefer
<abbr title="or, at least, to the extent possible thanks to tooling and ABI incompatibilities &#x1F643;">cross-platform</abbr>
build tools such as Meson and CMake.

Additionally, I have written a few OpenGL applications from scratch as a learning experience.

I like to use C++ and Rust for systems programming and for writing low-level, high-performance tools.
When writing C++, I prefer using Meson and Clang if they are available.
I generally prefer C++ over C due to the increased flexibility,
but if I am writing an extensible application (or _really_ low-level code) C is really the only viable option.
        """.trimIndent()
    ),
    SkillCategory(
        ".NET", listOf(
            Skill(5.00, "C#", "8", "cs.svg"),
            Skill(3.75, "ASP.NET", "Core 3", "net.svg")
        ),
        _text = """
C# has become increasingly more appealing over the years, especially with the release of .NET Core
and the solidification of the .NET Standard. While it was easy to start out with due to my prior Java knowledge,
my work experience at a sizeable software company, in addition to my work on various personal projects,
have enabled me to fully grasp proper C# idioms.

I like to use C# to write cloud-based web applications (thanks, Azure).
        """.trimIndent()
    ),
    SkillCategory(
        "Tools", listOf(
            Skill(4.75, "GNU Coreutils", _url = "https://www.gnu.org/software/coreutils/", _iconPath = "gnu.svg"),
            Skill(4.75, "Linux", _iconPath = "linux-tux.svg"),
            Skill(4.75, "macOS", _url = "https://www.apple.com/macos/", _iconPath = "macos-finder.png"),
            Skill(4.75, "Windows", _url = "https://www.microsoft.com/windows", _iconPath = "windows.svg"),
            Skill(4.50, "Git", _url = "https://git-scm.com/"),

            Skill(Double.NaN, "JetBrains Toolbox", _url = "https://www.jetbrains.com/toolbox-app/", _iconPath = "toolbox.svg"),
            Skill(5.00, "IntelliJ IDEA", _url = "https://www.jetbrains.com/idea/", _iconPath = "idea.svg"),
            Skill(4.75, "Android Studio", _url = "https://developer.android.com/studio/", _iconPath = "android-studio.svg"),
            Skill(4.50, "Rider", _url = "https://www.jetbrains.com/rider/"),
            Skill(4.50, "PyCharm", _url = "https://www.jetbrains.com/pycharm/"),

            Skill(4.50, "Visual Studio Code, VSCodium", _url = "https://code.visualstudio.com/", _iconPath = "vscode.svg"),
            Skill(4.00, "Microsoft Visual Studio", _url = "https://visualstudio.microsoft.com/", _iconPath = "vs.svg"),
            Skill(Double.NaN, "JetBrains ReSharper", _url = "https://www.jetbrains.com/resharper/", _iconPath = "resharperpp.svg"),

            Skill(4.00, "Docker", _url = "https://www.docker.com/", _iconPath = "docker-moby.png"),

            Skill(Double.NaN, "LLVM, Clang", _url = "https://llvm.org/", _iconPath = "llvm-head.png"),

            Skill(
                4.50, "TeX (LaTeX, XeTeX)", _iconPath = "tex-bg.svg",
                _richTooltipText = "<img style='display: inline; height: 1em; vertical-align: middle;' src='/static/image/logo/tex.svg'/> (<img style='display: inline; height: 1em; vertical-align: middle;' src='/static/image/logo/latex.svg'/>, <img style='display: inline; height: 1em; vertical-align: middle;' src='/static/image/logo/xetex.svg'/>)"
            ),
            Skill(Double.NaN, "Asciidoc, Asciidoctor", _url = "http://asciidoc.org/", _iconPath = "asciidoctor.svg"),
            Skill(Double.NaN, "Microsoft Office", _url = "https://www.office.com/", _iconPath = "office.svg"),
            Skill(4.00, "Adobe After Effects", _url = "https://www.adobe.com/products/aftereffects.html", _iconPath = "ae.svg"),
            Skill(Double.NaN, "Doxygen", _url = "http://www.doxygen.nl/", _iconPath = "doxygen-bowl.png")
        ),
        _small = true,
        _text = """
As I usually work on Arch Linux, I have had quite a while to become familiar with core POSIX utilities
and other useful tools.
However, I have also had experience using Windows and macOS for both casual and professional purposes.

I am relatively comfortable using Git for version control.
        """.trimIndent()
    ),
    SkillCategory(
        "Other", listOf(
            Skill(4.75, "JavaScript", "ES10", "js.svg"),
            Skill(4.75, "CSS", "3", "css3.svg"),
            Skill(4.25, "Python", "3"),
            Skill(4.00, "TypeScript", "3", "ts.svg")
        ),
        _text = """
Fun fact: This site itself was
<a href="https://github.com/stuhlmeier/stuhlmeier.github.io">written from scratch with JavaScript and Kotlin</a>,
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
