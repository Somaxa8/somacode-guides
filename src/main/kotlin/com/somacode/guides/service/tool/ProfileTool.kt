package com.somacode.guides.service.tool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ProfileTool {

    enum class Profile { PROD, DEV }

    @Autowired lateinit var environment: Environment

    val isProd: Boolean
        get() {
            val profiles: Array<String> = environment.activeProfiles
            if (profiles.isNotEmpty()) {
                for (profile in profiles) {
                    if (profile == "prod") {
                        return true
                    }
                }
            }
            return false
        }

    val isDev: Boolean
        get() {
            val profiles: Array<String> = environment.activeProfiles
            return profiles.isEmpty()
        }
}