import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        desktopMain.dependencies {
            
            implementation(compose.desktop.currentOs)

            implementation("org.postgresql:postgresql:42.7.3")

            implementation("org.jetbrains.jewel:jewel-int-ui-standalone:0.15.2")
            implementation("org.jetbrains.jewel:jewel-int-ui-decorated-window:0.15.2")
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material")
            }

            implementation ("org.jetbrains.compose.material3:material3-desktop:1.6.1")

            implementation("io.github.windedge.table:table:0.1.6")

            implementation("org.jetbrains.exposed:exposed-core:0.49.0")
            implementation("org.jetbrains.exposed:exposed-crypt:0.49.0")
            implementation("org.jetbrains.exposed:exposed-dao:0.49.0")
            implementation("org.jetbrains.exposed:exposed-jdbc:0.49.0")
            implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.49.0")
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.haskins.staffmanagement.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.haskins.staffmanagement"
            packageVersion = "1.0.0"

            macOS {
                dockName = "Staff Management"
                bundleID = "io.haskins.staffmanagement"
                iconFile = file("icons/jewel.icons")
            }
        }
    }
}
