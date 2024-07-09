rootProject.name = "agile-gui"

include("core")
findProject(":core")?.name = "agile-gui"

listOf("kotlin").forEach {
    include(it)
    findProject(":$it")?.name = "agile-gui-$it"
}

include("test-plugin")
