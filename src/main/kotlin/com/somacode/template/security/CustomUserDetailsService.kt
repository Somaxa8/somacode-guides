package com.somacode.template.security

import com.somacode.template.entity.User
import com.somacode.template.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component("customUserDetailsService")
class CustomUserDetailsService: UserDetailsService {

    @Autowired lateinit var userRepository: UserRepository


    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userRepository.findByEmail(username)
        return if (user != null) {
            val grantedAuthorities = ArrayList<GrantedAuthority>()
            for (authority in user.authorities) {
                grantedAuthorities.add(SimpleGrantedAuthority(authority.role.toString()))
            }
            CustomUserDetails(username, user.password, grantedAuthorities, user.id)
        } else {
            throw UsernameNotFoundException("User $username was not found in the database")
        }
    }

    class CustomUserDetails: org.springframework.security.core.userdetails.User {
        var id: Long? = null
            private set

        constructor(username: String?, password: String?, authorities: Collection<GrantedAuthority?>?, id: Long?) : this(username, password, authorities) {
            this.id = id
        }
        constructor(username: String?, password: String?, authorities: Collection<GrantedAuthority?>?) : super(username, password, authorities) {}
        constructor(username: String?, password: String?, enabled: Boolean, accountNonExpired: Boolean, credentialsNonExpired: Boolean, accountNonLocked: Boolean, authorities: Collection<GrantedAuthority?>?) : super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities) {}

    }

}