package org.training360.team.converters;

import org.mapstruct.Mapper;
import org.training360.team.dtos.PlayerDto;
import org.training360.team.dtos.TeamDto;
import org.training360.team.model.Player;
import org.training360.team.model.Team;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Converter {

    PlayerDto playerToDto(Player player);
    List<PlayerDto> playerToDto(List<Player> players);

    TeamDto teamToDto(Team team);
    List<TeamDto> teamToDto(List<Team> teams);
}
