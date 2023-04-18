package org.training360.team.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.training360.team.converters.Converter;
import org.training360.team.dtos.CreatePlayerCommand;
import org.training360.team.dtos.PlayerDto;
import org.training360.team.exceptions.PlayerNotFoundException;
import org.training360.team.model.Player;
import org.training360.team.repositories.PlayerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {

    PlayerRepository playerRepository;
    Converter converter;

    public List<PlayerDto> findAllPlayers() {
        return converter.playerToDto(playerRepository.findAll());
    }

    public PlayerDto createPlayer(CreatePlayerCommand command) {
        Player player = Player.builder()
                .name(command.getName())
                .dateOfBirth(command.getDateOfBirth())
                .position(command.getPosition())
                .build();
        return converter.playerToDto(playerRepository.save(player));
    }


    public void deletePlayerById(long id) {
        Player player = playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
        playerRepository.delete(player);
    }
}
