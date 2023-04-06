plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("checkstyle")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

checkstyle {
    toolVersion = "10.3.1"
    configFile = file("checkstyle.xml")
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("net.sf.supercsv:super-csv:2.4.0")
    implementation("com.puppycrawl.tools:checkstyle:10.3.1")
    implementation("org.apache.httpcomponents:httpclient:4.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
