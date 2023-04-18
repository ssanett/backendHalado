package org.training360.team.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException() {
        super("Player was not found");
    }
}
