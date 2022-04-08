package com.somacode.guides.config

import com.somacode.guides.entity.User
import com.somacode.guides.security.SecurityTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditingConfig")
class AuditingConfig : AuditorAware<User> {

    @Autowired
    lateinit var securityTool: SecurityTool


    override fun getCurrentAuditor(): Optional<User> {
        if (securityTool.isAuthenticated()) {
            val user = User()
            user.id = securityTool.getUserId()
            return Optional.of(user)
        } else {
            return Optional.empty()
        }
    }
}