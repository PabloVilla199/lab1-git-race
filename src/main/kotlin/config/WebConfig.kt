package es.unizar.webeng.hello.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

/**
 * Internationalization (i18n) configuration for the web application.
 * 
 * Defines the default languaje as English and allows changing the language 
 * via the "lang" request parameter.
 * 
 * 
 */
@Configuration
class WebConfig : WebMvcConfigurer {

    /**
     *  Defines the LocaleResolver that will maintain the user's language 
     * 
     * SesionLocaleResolver: Stores language in the HTTP session.
     * Default locale is set to English.
     * 
     */

    @Bean
    fun localeResolver(): LocaleResolver {
        val sessionLocaleResolver = SessionLocaleResolver()
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH)
        return sessionLocaleResolver
    }

    /**
     *  Defines the LocaleChangeInterceptor that will intercept requests 
     *  and change the language if the "lang" parameter is present.
     *  
     *  When a request contains a parameter named "lang", the interceptor
     *  will change the locale to the value of that parameter.  
     */

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "lang"
        return localeChangeInterceptor
    }

    /**
     * We add the localeChangeInterceptor to the application's interceptor registry.
     * This ensures that the interceptor is applied to all incoming requests.
     * allowing that any request could change the current locale
     *      
     */

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }
}