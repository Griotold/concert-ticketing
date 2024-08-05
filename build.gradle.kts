import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.3.0"
    jacoco
}

group = "org.griotold"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.11"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // redisson - 분산락 pub/sub 방식
    implementation("org.redisson:redisson-spring-boot-starter:3.18.0")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // h2
    runtimeOnly("com.h2database:h2")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("com.github.codemonstur:embedded-redis:1.0.0")

    // test container
    testImplementation("org.testcontainers:testcontainers:1.19.8")
    testImplementation("org.testcontainers:junit-jupiter:1.19.8")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")

    useJUnitPlatform()

    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
        html.required = true
        csv.required = false
    }

    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = BigDecimal(0.30)
            }
        }

        rule {
            enabled = false

            element = "CLASS"

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = BigDecimal(0.50)
            }

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = BigDecimal(0.50)
            }

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = BigDecimal(300)
            }

            excludes = listOf(
                "*.test.*",
            )
        }
    }
}
// 린트 임시로 막아둠
tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask> {
    enabled = false
}

