package br.com.eduardovpessoa

import br.com.eduardovpessoa.model.dto.LinkDTO
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LinkResourceTest {

    private lateinit var dto: LinkDTO
    private lateinit var emptyDTO: LinkDTO

    @BeforeAll
    fun setup() {
        emptyDTO = LinkDTO()
        dto = LinkDTO()
        dto.url = "https://letscode.com.br"
    }

    @Test
    fun `check url error giving an empty url`() {
        given()
                .body(emptyDTO)
                .contentType(ContentType.JSON)
                .`when`().post("/url/check")
                .then()
                .statusCode(400)
    }

    @Test
    fun `register a new and a duplicated link`() {
        given()
                .body(dto)
                .contentType(ContentType.JSON)
                .`when`().post("/url/new")
                .then()
                .statusCode(201)
        given()
                .body(dto)
                .contentType(ContentType.JSON)
                .`when`().post("/url/new")
                .then()
                .statusCode(200)
    }

    @Test
    fun `list all links`() {
        given()
                .`when`().get("/url")
                .then()
                .statusCode(200)
    }

    @Test
    fun `delete all links`() {
        given()
                .`when`().delete("/url/remove")
                .then()
                .statusCode(200)
    }

}