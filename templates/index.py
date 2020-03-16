from dataclasses import dataclass
from pathlib import Path
from typing import Optional, Union


@dataclass
class Skill:
    proficiency: float
    name: str
    qualifier: Optional[str]
    icon_path: Optional[Union[Path, str]] = None
    text: Optional[str] = None

    def name(self) -> str: return self.name

    def proficiency(self) -> float: return self.proficiency

    def qualifier(self) -> Optional[str]: return self.qualifier

    def description(self) -> str: return self.text or ""

    def iconPath(self) -> Path:
        return Path("/static/image/logo/") / (self.icon_path or f"{self.name.lower().strip()}.svg")

    def meterPercentage(self) -> str: return f"{round(self.proficiency * 20)}%"

    def meterClasses(self) -> str:
        return "meter-good" if self.proficiency >= 3.5 else "meter-ok" if self.proficiency >= 2 else "meter-bad"

    def meterStyle(self) -> str:
        return f"width: {self.meterPercentage()}"


@dataclass
class SkillCategory:
    name: str
    skills: [Skill]
    text: Optional[str] = None

    def name(self) -> str: return self.name

    def skills(self) -> [Skill]: return self.skills

    def description(self) -> str: return self.text or ""


skills: [SkillCategory] = [
    SkillCategory("JVM", [
        Skill(5.00, "Java", "14"),
        Skill(5.00, "Kotlin", "1.3"),
        Skill(3.50, "Spring", "5"),
        Skill(4.25, "Gradle", "6"),
        Skill(3.50, "Maven", "3", "apache.svg")
    ], """
        I have most experience with the JVM ecosystem; Kotlin and Gradle are tools I use almost daily.
    """),
    SkillCategory("Native development", [
        Skill(4.75, "C++", "20", "cpp.svg"),
        Skill(3.50, "C", "18"),
        Skill(1.75, "Rust", None),
        Skill(3.75, "CMake", "3.14+"),
        Skill(3.50, "Meson", None, "meson.png")
    ], """
        I have used C++ for a while, beginning with various competitive programming usages and moving on
        to proper native development. While I primarily develop on Linux, I tend to prefer
        <abbr title="or, at least, to the extent possible thanks to tooling and ABI incompatibilities &#x1F643;">cross-platform</abbr> \
        build tools such as CMake and Meson.
    """),
    SkillCategory(".NET", [
        Skill(5.00, "C#", "8", "cs.svg"),
        Skill(3.75, "ASP.NET", "Core 3", "net.svg")
    ], """
        There isn't much to say here; ASP.NET is simply the framework I have used the most.
    """),
    SkillCategory("Tools", [
        Skill(4.50, "Git", None)
    ]),
    SkillCategory("Other", [
        Skill(4.75, "JavaScript", "ES10", "js.svg"),
        Skill(4.75, "CSS", "3", "css3.svg"),
        Skill(4.25, "Python", "3"),
        Skill(4.00, "TypeScript", "3", "ts.svg")
    ], """
        This site itself was written from scratch with JavaScript, CSS and Python,
        using <code>pystache</code> as a templating engine.
    """)
]
