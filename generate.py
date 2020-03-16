import os
from pathlib import Path
from typing import Any

import pystache

from templates import index

renderer = pystache.Renderer(escape=lambda u: u)


def load_template(template_name: str) -> Any:
    return pystache.parse((Path("templates") / template_name).read_text("utf-8"))


def write_rendered(template_name: str, content: Any) -> None:
    name = os.path.splitext(template_name)[0]
    output_path = Path("build") / f"{name}.html"

    try:
        output_path.parent.mkdir()
    except FileExistsError:
        pass

    output_path.write_text(content, "utf-8")


def render_index(template: Any) -> Any:
    return renderer.render(template, {"categories": index.skills})


def main() -> None:
    index_template = "index.mustache"

    write_rendered(index_template, render_index(load_template(index_template)))


if __name__ == "__main__":
    main()
