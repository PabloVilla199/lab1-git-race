package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(HelloController::class, HelloApiController::class)
class HelloControllerMVCTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return home page with default message`() {
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("name", equalTo("")))
            .andExpect(model().attributeExists("currentLocale"))
    }
    
    @Test
    fun `should return home page with personalized message`() {
        mockMvc.perform(get("/").param("name", "Developer"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("name", equalTo("Developer")))
            .andExpect(model().attributeExists("currentLocale"))
    }
    
    @Test
    fun `should return API response as JSON`() {
        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.locale").exists())
    }

    /**
     * Test case to verify that returns home page with English locale and default message
     */
    @Test
    fun `should return home page in English with default message`() {
        mockMvc.perform(get("/").param("lang", "en"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("name", equalTo("")))
            .andExpect(model().attributeExists("currentLocale"))
            .andExpect(model().attribute("currentLocale", equalTo("en")))
            .andExpect(model().attribute("message", equalTo("Welcome to the Modern Web App!")))
    }

    /**
     * Test case to verify that returns home page with Spanish locale and default message
     */
    @Test
    fun `should return home page in Spanish with default message`() {
        mockMvc.perform(get("/").param("lang", "es"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("name", equalTo("")))
            .andExpect(model().attributeExists("currentLocale"))
            .andExpect(model().attribute("message", equalTo("¡Bienvenido a la Aplicación Web")))
    }

    /**
     *  Test case to verify that returns home page with Spanish locale whit personalized message "Pablo"
     */

    @Test
    fun `should return home page in Spanish and personalized message for Pablo`() {
        mockMvc.perform(get("/").param("lang", "es").param("name", "Pablo"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("name", equalTo("Pablo")))
            .andExpect(model().attributeExists("currentLocale"))
            .andExpect(model().attribute("message", equalTo("¡Hola, Pablo!")))
    }
}