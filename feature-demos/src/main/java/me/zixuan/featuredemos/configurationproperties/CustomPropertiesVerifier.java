package me.zixuan.featuredemos.configurationproperties;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomPropertiesVerifier implements ApplicationRunner {
    private final CustomProperties customProperties;
    private final Item item;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // simple properties
        System.out.println(customProperties.getName());
        System.out.println(customProperties.getNumber());

        // nested properties
        // list
        System.out.println(customProperties.getCustomList().get(0));
        System.out.println(customProperties.getCustomList().get(1));
        // map
        System.out.println(customProperties.getCustomMap().get("anyKey1"));
        System.out.println(customProperties.getCustomMap().get("anyKey2"));

        // nested inner class
        System.out.println(customProperties.getFirstNestedCustom().getNestedName());
        System.out.println(customProperties.getFirstNestedCustom().getNestedNumber());
        System.out.println(customProperties.getSecondNestedCustom().getNestedName());
        System.out.println(customProperties.getSecondNestedCustom().getNestedNumber());

        // nested outer class
        System.out.println(customProperties.getNestedOuterClass().getNestedOuterClassName());

        // bean property
        System.out.println(item.getItemField());


    }
}
