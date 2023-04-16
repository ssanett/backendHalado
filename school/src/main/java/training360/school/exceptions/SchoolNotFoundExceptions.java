package training360.school.exceptions;

public class SchoolNotFoundExceptions extends RuntimeException{

    public SchoolNotFoundExceptions(long id) {
        super(String.format("School not found with id: %d",id));
    }
}
