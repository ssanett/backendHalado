package org.training360.team.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.training360.team.converters.Converter;
import org.training360.team.dtos.CreatePlayerCommand;
import org.training360.team.dtos.CreateTeamCommand;
import org.training360.team.dtos.TeamDto;
import org.training360.team.dtos.UpdateWithExistingPlayerCommand;
import org.training360.team.exceptions.PlayerNotFoundException;
import org.training360.team.exceptions.TeamNotFoundException;
import org.training360.team.model.Player;
import org.training360.team.model.Team;
import org.training360.team.repositories.PlayerRepository;
import org.training360.team.repositories.TeamRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {

    Converter converter;
    TeamRepository teamRepository;
    PlayerRepository playerRepository;

    public List<TeamDto> findTeams() {
        return converter.teamToDto(teamRepository.findAll());
    }


    public TeamDto createTeam(CreateTeamCommand command) {
        Team team = Team.builder()
                .name(command.getName()).build();

        return converter.teamToDto(teamRepository.save(team));
    }


    public TeamDto updateTeamWithNewPlayer(long id, CreatePlayerCommand command) {
        Player player = Player.builder()
                .name(command.getName())
                .dateOfBirth(command.getDateOfBirth())
                .position(command.getPosition()).build();

        Team team = teamRepository.findById(id).orElseThrow(TeamNotFoundException::new);
        team.getPlayers().add(playerRepository.save(player));
        player.setTeam(team);

        return converter.teamToDto(teamRepository.save(team));
    }

    public TeamDto updateTeamWithPlayer(long id, UpdateWithExistingPlayerCommand command) {
        Player player = playerRepository.findById(command.getPlayerId()).orElseThrow(PlayerNotFoundException::new);
        Team team = teamRepository.findById(id).orElseThrow(TeamNotFoundException::new);
        if (player.getTeam() == null || checkTeamsPosition(team, player)) {
            team.addPlayer(player);
        }
        return converter.teamToDto(team);
    }

    private boolean checkTeamsPosition(Team team, Player player) {
        return team.getPlayers().stream().filter(t -> t.getPosition().equals(player.getPosition()))
                .toList().size() < 2;

    }
}
