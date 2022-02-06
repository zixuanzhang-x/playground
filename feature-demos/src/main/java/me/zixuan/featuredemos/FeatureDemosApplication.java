package me.zixuan.featuredemos;

import me.zixuan.featuredemos.configurationproperties.CustomProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CustomProperties.class)
//@ConfigurationPropertiesScan("me.zixuan.featuredemos.configurationproperties")
public class FeatureDemosApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeatureDemosApplication.class, args);
    }
}
