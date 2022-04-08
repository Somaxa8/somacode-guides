package com.somacode.template.config

import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import java.lang.reflect.Method

@Configuration
class CacheConfig: CachingConfigurerSupport() {


    @Bean
    override fun keyGenerator(): KeyGenerator {
        return CustomKeyGenerator()
    }

    class CustomKeyGenerator: KeyGenerator {
        override fun generate(target: Any, method: Method, vararg params: Any?): Any {
            return "${target.javaClass.simpleName}_${method.name}_${StringUtils.arrayToDelimitedString(params, "_")}"
        }
    }

}