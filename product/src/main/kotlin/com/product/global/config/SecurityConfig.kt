package com.product.global.config


import com.product.global.filter.AuthenticationFilter
import com.product.global.handler.CustomAccessDeniedHandler
import com.product.global.handler.CustomAuthenticationEntryPointHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customAuthenticationEntryPointHandler: CustomAuthenticationEntryPointHandler,
    private val authenticationFilter: AuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.formLogin { it.disable() }
            .httpBasic { it.disable() }

        http.csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }

        http.exceptionHandling { handling ->
            handling.accessDeniedHandler(customAccessDeniedHandler)
            handling.authenticationEntryPoint(customAuthenticationEntryPointHandler)
        }

        http.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.authorizeHttpRequests { httpRequests ->
            // health check
            httpRequests.requestMatchers(HttpMethod.GET, "/product/health").permitAll()

            // product
            httpRequests.requestMatchers(HttpMethod.GET, "/product").authenticated()
            httpRequests.requestMatchers(HttpMethod.POST, "/product").authenticated()
            httpRequests.requestMatchers(HttpMethod.GET, "/product/{product_id}").authenticated()

            httpRequests.anyRequest().denyAll()
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        // plz custom allowed client origins
        configuration.allowedOrigins = listOf("*")

        configuration.allowedMethods = listOf(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name()
        )

        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}
