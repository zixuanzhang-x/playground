package me.zixuan.playground.blogkt

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogKtApplication

// https://spring.io/guides/tutorials/spring-boot-kotlin/
fun main(args: Array<String>) {
    runApplication<BlogKtApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}