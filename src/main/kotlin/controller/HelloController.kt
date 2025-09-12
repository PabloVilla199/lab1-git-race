package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

/**
 * Controller for rendering the main web page with localized greetings.
 * Supports changing the language via the '?lang=xx' parameter.
 */
@Controller
class HelloController(
    @Autowired private val messageSource: MessageSource
) {

    /**
     * Handles requests to the root path ("/").
     * 
     * HowItWorks:
     * - Checks if a name is provided by the user.
     * - Uses the locale resolved by WebConfig (default or changed via '?lang=xx').
     * - MessageSource looks into the correct properties file for that locale.
     * - Returns the greeting message in the selected language.
     * - Adds the message and current locale to the model for the HTML view.
     */
    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String,
        locale: Locale
    ): String {
        val greeting = if (name.isNotBlank()) {
            messageSource.getMessage("greeting.hello", arrayOf(name), locale)
        } else {
            messageSource.getMessage("app.message", null, locale)
        }

        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        model.addAttribute("currentLocale", locale.language)

        return "welcome"
    }
}

/**
 * REST API controller for returning localized greetings in JSON format.
 * Supports '?lang=xx' for language selection.
 */
@RestController
class HelloApiController(
    @Autowired private val messageSource: MessageSource
) {

    /**
     * Handles requests to "/api/hello".
     * 
     * HowItWorks:
     * - Checks if the default name "World" is used.
     * - Uses the locale resolved by WebConfig (default or via '?lang=xx').
     * - MessageSource finds the message in the appropriate properties file.
     * - Returns the greeting in the selected language with timestamp and locale info.
     */
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(
        @RequestParam(defaultValue = "World") name: String,
        locale: Locale
    ): Map<String, String> {
        val greeting = if (name == "World") {
            messageSource.getMessage("greeting.default", null, locale)
        } else {
            messageSource.getMessage("greeting.hello", arrayOf(name), locale)
        }

        return mapOf(
            "message" to greeting,
            "timestamp" to Instant.now().toString(),
            "locale" to locale.toString()
        )
    }
}
