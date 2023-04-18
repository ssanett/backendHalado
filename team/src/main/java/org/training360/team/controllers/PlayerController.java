package org.training360.team.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.team.dtos.CreatePlayerCommand;
import org.training360.team.dtos.PlayerDto;
import org.training360.team.services.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@AllArgsConstructor
public class PlayerController {

    PlayerService playerService;

    @GetMapping
    public List<PlayerDto> findAllPlayers(){
        return playerService.findAllPlayers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto createPlayer(@Valid @RequestBody CreatePlayerCommand command){
        return playerService.createPlayer(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePlayerById(@PathVariable("id") long id){
        playerService.deletePlayerById(id);
    }
}
