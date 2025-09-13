package es.unizar.webeng.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should return home page with modern title and client-side HTTP debug`() {
        val response = restTemplate.getForEntity("http://localhost:$port", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Modern Web App")
        assertThat(response.body).contains("Welcome to Modern Web App")
        assertThat(response.body).contains("Interactive HTTP Testing &amp; Debug") 
        assertThat(response.body).contains("Client-Side Educational Tool")
    }

    @Test
    fun `should return personalized greeting when name is provided`() {
        val response = restTemplate.getForEntity("http://localhost:$port?name=Developer", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Hello, Developer!")
    }

    @Test
    fun `should return API response with timestamp`() {
        val response = restTemplate.getForEntity("http://localhost:$port/api/hello?name=Test", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
        assertThat(response.body).contains("Hello, Test!")
        assertThat(response.body).contains("timestamp")
        assertThat(response.body).contains("locale")
    }


    @Test
    fun `should serve Bootstrap CSS correctly`() {
        val response = restTemplate.getForEntity("http://localhost:$port/webjars/bootstrap/5.3.3/css/bootstrap.min.css", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("body")
        assertThat(response.headers.contentType).isEqualTo(MediaType.valueOf("text/css"))
    }

    @Test
    fun `should expose actuator health endpoint`() {
        val response = restTemplate.getForEntity("http://localhost:$port/actuator/health", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("UP")
    }
    
    @Test
    fun `should display client-side HTTP debug interface`() {
        val response = restTemplate.getForEntity("http://localhost:$port?name=Student", String::class.java)
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).contains("Interactive HTTP Testing &amp; Debug") 
        assertThat(response.body).contains("Client-Side Educational Tool")
        assertThat(response.body).contains("Web Page Greeting")
        assertThat(response.body).contains("API Endpoints")
        assertThat(response.body).contains("Health Check")
        assertThat(response.body).contains("Learning Notes:")
    }

    /**
     * Test case that return English page by default.
     * 
     */
    @Test
    fun `should return English page by default`() {
            val response = restTemplate.getForEntity("http://localhost:$port?lang=en", String::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).contains("Welcome to the Modern Web App!")
            assertThat(response.body).doesNotContain("¡Bienvenido a la Aplicación Web Moderna!")
            assertThat(response.body).contains("Interactive HTTP Testing &amp; Debug")
            assertThat(response.body).contains("Client-Side Educational Tool")
    }

    /**
     * Test case that return Spanish page when requested.
     * 
     */
    @Test
    fun `should return Spanish page when requested`() {
            val response = restTemplate.getForEntity("http://localhost:$port?lang=es", String::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).contains("¡Bienvenido a la Aplicación Web")
            assertThat(response.body).doesNotContain("Welcome to the Modern Web App!")
            assertThat(response.body).contains("HTTP Interactivas y Depuración")
            assertThat(response.body).contains("Herramienta Educativa del Cliente")
    }

    /**
     * Test case return spanish personalized greeting for Pablo.
     */

    @Test
    fun `should return spanish personalized greeting for Pablo`() {
            val response = restTemplate.getForEntity("http://localhost:$port?name=Pablo&lang=es", String::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).contains("¡Hola, Pablo!")
            assertThat(response.body).doesNotContain("Hello, Pablo!")
            assertThat(response.body).doesNotContain("Welcome to the Modern Web App!")
            assertThat(response.body).contains("Pruebas HTTP Interactivas y Depuración")
            assertThat(response.body).contains("Herramienta Educativa del Cliente")
            
    }

    /**
     * Test case sholud return API response in spanish when requested.
     */

    @Test
    fun `should return API response in spanish when requested`() {      
            val response = restTemplate.getForEntity("http://localhost:$port/api/hello?name=Test&lang=es", String::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
            assertThat(response.body).contains("¡Hola, Test!")
            assertThat(response.body).doesNotContain("Hello, Test!")
            assertThat(response.body).contains("timestamp")
            assertThat(response.body).contains("locale")
    }
         

}
