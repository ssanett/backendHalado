package org.training360.team.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.training360.team.dtos.CreatePlayerCommand;
import org.training360.team.dtos.CreateTeamCommand;
import org.training360.team.dtos.TeamDto;
import org.training360.team.dtos.UpdateWithExistingPlayerCommand;
import org.training360.team.services.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {

    TeamService service;

    @GetMapping
    public List<TeamDto> findTeams(){
        return service.findTeams();
    }

    @PostMapping
    public TeamDto createTeam(@RequestBody CreateTeamCommand command){
        return service.createTeam(command);
    }

    @PostMapping("/{id}/players")
    public TeamDto updateTeamWithNewPlayer(@PathVariable("id") long id, @RequestBody CreatePlayerCommand command){
        return service.updateTeamWithNewPlayer(id,command);
    }

    @PutMapping("/{id}/players")
    public TeamDto updateTeamWithPlayer(@PathVariable("id") long id,@RequestBody UpdateWithExistingPlayerCommand command){
        return service.updateTeamWithPlayer(id, command);
    }
}
