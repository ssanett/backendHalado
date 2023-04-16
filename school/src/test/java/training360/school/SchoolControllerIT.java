package training360.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import training360.school.dtos.CreateSchoolCommand;
import training360.school.dtos.CreateStudentCommand;
import training360.school.dtos.SchoolDto;
import training360.school.dtos.StudentDto;
import training360.school.model.SchoolAgeStatus;
import training360.school.services.SchoolService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from students", "delete from schools"})
public class SchoolControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    SchoolService service;

    SchoolDto school;
    StudentDto studentDto;

    @BeforeEach
    void init() {
        school = webTestClient.post()
                .uri("/api/schools")
                .bodyValue(new CreateSchoolCommand("Petőfi Sándor School", "1123", "Budapest", "Petőfi u.", 8))
                .exchange().expectStatus().isCreated()
                .expectBody(SchoolDto.class).returnResult().getResponseBody();

        webTestClient.post()
                .uri("/api/schools")
                .bodyValue(new CreateSchoolCommand("Radnóti Miklós School", "8000", "Veszprém", "Petőfi u.", 17))
                .exchange();

        SchoolDto schoolWithStudent = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/schools/{id}/students").build(school.getId()))
                .bodyValue(new CreateStudentCommand("John Doe", LocalDate.of(2012, 11, 7)))
                .exchange().expectStatus().isCreated()
                .expectBody(SchoolDto.class).returnResult().getResponseBody();

        studentDto = schoolWithStudent.getStudents().get(0);

    }

    @Test
    void testCreateSchool() {

        assertEquals(school.getSchoolName(),"Petőfi Sándor School");
        assertEquals(school.getAddress().getCity(),"Budapest");
    }

    @Test
    void testCreateSchoolWithWrongName() {
        ProblemDetail problemDetail = webTestClient.post()
                .uri("/api/schools")
                .bodyValue(new CreateSchoolCommand("   ", "1123", "Budapest", "Petőfi u.", 8))
                .exchange()
                //.expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

       assertEquals(URI.create("schools/invalid_request"),problemDetail.getType());
        assertEquals("Schoolname cannot be blank",problemDetail.getDetail());

    }

    @Test
    void testCreateStudent() {
        assertEquals("John Doe", studentDto.getName());
        assertEquals(SchoolAgeStatus.SCHOOL_AGED, studentDto.getSchoolAgeStatus());
    }

    @Test
    void testCreateStudentWithWrongName() {
        ProblemDetail problemDetail = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/schools/{id}/students").build(school.getId()))
                .bodyValue(new CreateStudentCommand("   ", LocalDate.of(2012, 11, 7)))
                .exchange()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();


        assertEquals("Student name cannot be blank!",problemDetail.getDetail());
        assertEquals(URI.create("schools/invalid_request"), problemDetail.getType());
    }

    @Test
    void testCreateStudentWithWrongDate() {
        ProblemDetail problemDetail= webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/schools/{id}/students").build(school.getId()))
                .bodyValue(new CreateStudentCommand("Jack Doe", LocalDate.now().plusDays(5)))
                .exchange().expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

        assertEquals("Student date of birth must be in past!", problemDetail.getDetail());
    }

    @Test
    void testCreateStudentWithWrongSchool() {
        long wrongId = school.getId() + 10000;
        ProblemDetail problemDetail= webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/schools/{id}/students").build(wrongId))
                .bodyValue(new CreateStudentCommand("Jane Doe", LocalDate.of(2012, 11, 7)))
                .exchange().expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

        assertEquals(String.format("School not found with id: %d", wrongId), problemDetail.getDetail());
    }

    @Test
    void testGetAllSchools() {
        List<SchoolDto> result = webTestClient.get()
                .uri("api/schools")
                .exchange().expectBodyList(SchoolDto.class).returnResult().getResponseBody();

        assertThat(result).extracting(SchoolDto::getSchoolName)
                .containsExactly("Petőfi Sándor School", "Radnóti Miklós School");
    }

    @Test
    void testGetSchoolsByCity() {
        List<SchoolDto> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/schools").queryParam("city", "Veszprém").build())
                .exchange().expectBodyList(SchoolDto.class).returnResult().getResponseBody();

        assertThat(result).extracting(SchoolDto::getSchoolName).
                containsExactly("Radnóti Miklós School");
    }

    @Test
    void testFireStudent() {

        SchoolDto result = webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/schools/{id}/students/{stdId}").build(school.getId(), studentDto.getId()))
                .exchange()
                .expectBody(SchoolDto.class).returnResult().getResponseBody();

        assertThat(result.getStudents()).isEmpty();
    }

    @Test
    void testFireStudentWithWrongStudentId() {
        long wrongId = studentDto.getId() + 1000;
        ProblemDetail problem = webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/schools/{id}/students/{stdId}").build(school.getId(), wrongId))
                .exchange().expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

        assertEquals(String.format("Student not found with id: %d", wrongId), problem.getDetail());
    }

    @Test
    void testFireStudentWithNotInSchoolId() {

        SchoolDto other = webTestClient.post()
                .uri("/api/schools")
                .bodyValue(new CreateSchoolCommand("Other", "8000", "Veszprém", "Petőfi u.", 17))
                .exchange().expectBody(SchoolDto.class).returnResult().getResponseBody();

        StudentDto studentDtoOther = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/schools/{id}/students").build(other.getId()))
                .bodyValue(new CreateStudentCommand("John Doe", LocalDate.of(2012, 11, 7)))
                .exchange().expectBody(SchoolDto.class).returnResult().getResponseBody().getStudents().get(0);

        ProblemDetail problem = webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/schools/{id}/students/{stdId}").build(school.getId(), studentDtoOther.getId()))
                .exchange().expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

        assertEquals(String.format("Student not found with id: %d", studentDtoOther.getId()), problem.getDetail());
    }
}
