package org.training360.team.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.training360.team.dtos.CreatePlayerCommand;
import org.training360.team.dtos.PlayerDto;
import org.training360.team.model.PositionType;
import org.training360.team.services.PlayerService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from players"})
class PlayerControllerTestIT {

    @Autowired
    WebTestClient client;

    @Autowired
    PlayerService service;

    @Test
    void testAddNewPlayer(){
        PlayerDto result = client.post()
                .uri("/api/players")
                .bodyValue(new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10), PositionType.CENTER_BACK))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PlayerDto.class).returnResult().getResponseBody();

        assertEquals("John Doe",result.getName());
        assertEquals(1991,result.getDateOfBirth().getYear());
        assertEquals(PositionType.CENTER_BACK,result.getPosition());
    }

    @Test
    void testGetPlayers(){
        client.post()
                .uri("/api/players")
                .bodyValue(new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10), PositionType.CENTER_BACK))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PlayerDto.class).returnResult().getResponseBody();

        client.post()
                .uri("/api/players")
                .bodyValue(new CreatePlayerCommand("Jack Doe", LocalDate.of(1992,11,10), PositionType.RIGHT_WINGER))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PlayerDto.class).returnResult().getResponseBody();

        List<PlayerDto> result = client.get()
                .uri("/api/players")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBodyList(PlayerDto.class).returnResult().getResponseBody();

        assertThat(result).extracting(PlayerDto::getName)
                .containsExactly("John Doe","Jack Doe");


    }

    @Test
    void testDeletePlayerById(){
        PlayerDto result = client.post()
                .uri("/api/players")
                .bodyValue(new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10), PositionType.CENTER_BACK))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PlayerDto.class).returnResult().getResponseBody();

        PlayerDto expected = client.post()
                .uri("/api/players")
                .bodyValue(new CreatePlayerCommand("Jack Doe", LocalDate.of(1992,11,10), PositionType.RIGHT_WINGER))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PlayerDto.class).returnResult().getResponseBody();

        client.delete()
                .uri(uriBuilder-> uriBuilder.path("/api/players/{id}").build(result.getId()))
                .exchange()
                .expectStatus().isAccepted();

        List<PlayerDto> players = client.get()
                .uri("/api/players")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBodyList(PlayerDto.class).returnResult().getResponseBody();

        assertThat(players).size().isEqualTo(1);

    }

    @Test
    void testCreatePlayerWithInValidName(){


        ProblemDetail problemDetail = client.post()
                .uri("/api/players")
                .bodyValue(new CreatePlayerCommand("", LocalDate.of(1991,11,10), PositionType.CENTER_BACK))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

    }
}