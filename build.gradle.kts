import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jetbrains.compose") version "1.6.0-dev1440"
}

group = "io.haskins.staffmanagement"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

val exposedVersion: String by project
dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    // See https://github.com/JetBrains/Jewel/releases for the release notes
    implementation("org.jetbrains.jewel:jewel-int-ui-standalone:0.15.2")

    // Optional, for custom decorated windows:
    implementation("org.jetbrains.jewel:jewel-int-ui-decorated-window:0.15.2")

    // Do not bring in Material (we use Jewel)
    implementation(compose.desktop.currentOs) {
        exclude(group = "org.jetbrains.compose.material")
    }

    implementation("org.postgresql:postgresql:42.7.3")

    implementation("org.jetbrains.compose.material3:material3-desktop:1.6.1")
    implementation("org.jetbrains.compose.components:components-splitpane-desktop:1.6.1")

    implementation("io.github.windedge.table:table:0.1.6")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
}

compose.desktop {
    application {
        mainClass = "io.haskins.staffmanagement.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "StaffManagement"
            packageVersion = "1.0.0"
        }
    }
}
