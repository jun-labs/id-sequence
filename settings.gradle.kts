pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val jpaVersion: String by settings
    val liquibaseVersion: String by settings
    val liquibaseCoreVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(jpaVersion)
                "org.liquibase.gradle" -> useVersion(liquibaseVersion)
                "org.liquibase:liquibase-core" -> useVersion(liquibaseCoreVersion)
            }
        }
    }
}
rootProject.name = "sequence-generator"
