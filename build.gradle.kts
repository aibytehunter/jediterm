val projectVersion = rootProject.projectDir.resolve("VERSION").readText().trim() +
        if (System.getenv("INTELLIJ_DEPENDENCIES_BOT") == null) "-SNAPSHOT" else ""


allprojects {
    group = "org.jetbrains.jediterm"
    version = projectVersion
    layout.buildDirectory = rootProject.projectDir.resolve(".gradleBuild/" + project.name)
}

subprojects {
    repositories {
        mavenCentral()
    }
}