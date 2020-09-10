import de.fayard.refreshVersions.bootstrapRefreshVersions
import org.danilopianini.VersionAliases.justAdditionalAliases
buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("de.fayard.refreshVersions:refreshVersions:0.9.5")
        classpath("org.danilopianini:refreshversions-aliases:+")
    }
}
bootstrapRefreshVersions(justAdditionalAliases)

include(
    "graphstream-gs-algo",
    "graphstream-gs-core",
    "graphstream-gs-ui-swing"
)
rootProject.name = "graphstream"