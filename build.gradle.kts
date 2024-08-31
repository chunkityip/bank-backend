plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework:spring-context:6.1.11")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly ("com.h2database:h2")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	//ms
	// Bean Validation API
	implementation ("javax.validation:validation-api:2.0.1.Final")
	//ms
	// Hibernate Validator (Implementation of Bean Validation)
	implementation ("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	//ms
	// Optional: For more validation features
	implementation ("org.glassfish:javax.el:3.0.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
