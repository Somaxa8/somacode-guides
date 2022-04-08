package com.somacode.template.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebMvcConfig: WebMvcConfigurer {

    @Autowired lateinit var requestTimeInterceptor: RequestTimeInterceptor


    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestTimeInterceptor)
    }

    @Component
    class RequestTimeInterceptor : HandlerInterceptorAdapter() {
        override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
            request.setAttribute("requestTime", System.currentTimeMillis())
            return true
        }

        override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
            val time = request.getAttribute("requestTime") as Long
            val endpoint = request.servletPath
            if (endpoint.startsWith("/bower_components")
                    || endpoint.startsWith("/swagger-resources")
                    || endpoint.startsWith("/swagger-ui.html")
                    || endpoint.startsWith("/csrf")
                    || endpoint.startsWith("/error")
                    || endpoint.startsWith("/favicon")
                    || endpoint.startsWith("/dist")
                    || endpoint.startsWith("/webjars")
                    || endpoint.startsWith("/null")
                    || endpoint.startsWith("/storage")
                    || endpoint.startsWith("/plugins")) {
                return
            }
            val methodHttp = request.method
            val executionTime = (System.currentTimeMillis() - time).toInt()
            val content = "$methodHttp\t'$endpoint'\t in $executionTime ms"
            println("$content -> ${response.status}")
        }
    }

    // CORS
    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    class CorsConfig : Filter {
        @Throws(IOException::class, ServletException::class)
        override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
            val response = res as HttpServletResponse
            val request = req as HttpServletRequest
            response.setHeader("Access-Control-Allow-Origin", URL_CORS_ORIGIN)
            response.setHeader("Access-Control-Allow-Methods", URL_CORS_METHODS)
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Content-Type")
            response.setHeader("Access-Control-Max-Age", "3600")
            response.setHeader("Access-Control-Expose-Headers", "X-Total-Count")
            if ("OPTIONS".equals(request.method, ignoreCase = true)) {
                response.status = HttpServletResponse.SC_OK
            } else {
                chain.doFilter(req, res)
            }
        }

        companion object {
            private const val URL_CORS_ORIGIN = "*"
            private const val URL_CORS_METHODS = "*"
        }
    }

}