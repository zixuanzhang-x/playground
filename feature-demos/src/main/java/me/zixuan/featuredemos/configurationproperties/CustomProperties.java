package me.zixuan.featuredemos.configurationproperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;
import java.util.Map;

/* 1. Use @Component here or @EnableConfigurationProperties(CustomProperties.class)
 *    to register this bean.
 *
 * 2. Add annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
 *    to dependencies.
 *
 * 3. Rebuild project in IntelliJ then a new
 *    `out/production/classes/META-INF/spring-configuration-metadata.json` file
 *    will be generated.
 *
 * */
@ConfigurationProperties("custom")
@ConstructorBinding
@Data
public class CustomProperties {
    // simple properties
    // @NotBlank // JSR-303 format, need javax.validation:validation-api and an implementation such as: org.hibernate:hibernate-validator:4.3.1.Final
    // @Min(100)
    // @Max(999999)
    /* If any of these JSR-303 validations fails, then the main application would fail
       to start with an IllegalStateException, which reduces a lot of if-else checking. */
    private final int number;
    // @Length(max = 10, min = 3) // JSR-303 format
    // @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$") // JSR-303 format
    private final String name;

    // nested properties
    private final List<String> customList;
    private final Map<String, String> customMap;

    // nested inner class
    private final NestedCustom firstNestedCustom;
    private final NestedCustom secondNestedCustom;

    @Data
    public static class NestedCustom {
        private final String nestedName;
        private final long nestedNumber;
    }

    // nested outer class
    private final NestedOuterClass nestedOuterClass;
}

@Data
class NestedOuterClass {
    private final String nestedOuterClassName;
}
