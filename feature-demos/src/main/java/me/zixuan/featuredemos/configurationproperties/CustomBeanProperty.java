package me.zixuan.featuredemos.configurationproperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeanProperty {
    @Bean
    @ConfigurationProperties("item")
    public Item item() {
        return new Item();
    }
}

@Data
class Item {
    private String itemField;
}