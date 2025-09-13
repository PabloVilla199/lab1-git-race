package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.context.MessageSource
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap
import java.util.*

class HelloControllerUnitTests {
    private lateinit var messageSource: MessageSource
    private lateinit var controller: HelloController
    private lateinit var model: Model
    
    @BeforeEach
    fun setup() {
        messageSource = mock(MessageSource::class.java)
        controller = HelloController(messageSource)
        model = ExtendedModelMap()
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val locale = Locale.ENGLISH
        val expectedMessage = "Welcome to the Modern Web App!"
        
        `when`(messageSource.getMessage("app.message", null, locale))
            .thenReturn(expectedMessage)
        
        val view = controller.welcome(model, "", locale)
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo(expectedMessage)
        assertThat(model.getAttribute("name")).isEqualTo("")
        assertThat(model.getAttribute("currentLocale")).isEqualTo("en")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val locale = Locale.ENGLISH
        val name = "Developer"
        val expectedMessage = "Hello, Developer!"
        
        `when`(messageSource.getMessage("greeting.hello", arrayOf(name), locale))
            .thenReturn(expectedMessage)
        
        val view = controller.welcome(model, name, locale)
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo(expectedMessage)
        assertThat(model.getAttribute("name")).isEqualTo(name)
        assertThat(model.getAttribute("currentLocale")).isEqualTo("en")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController(messageSource)
        val locale = Locale.ENGLISH
        val expectedMessage = "Hello, Test!"
        
        `when`(messageSource.getMessage("greeting.hello", arrayOf("Test"), locale))
            .thenReturn(expectedMessage)
        
        val response = apiController.helloApi("Test", locale)
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response).containsKey("locale")
        assertThat(response["message"]).isEqualTo(expectedMessage)
        assertThat(response["locale"]).isEqualTo("en")
        assertThat(response["timestamp"]).isNotNull()
    }
}