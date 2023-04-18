package org.training360.team.exceptions;

public class TeamNotFoundException extends RuntimeException{

    public TeamNotFoundException() {
        super("Team not found");
    }
}
