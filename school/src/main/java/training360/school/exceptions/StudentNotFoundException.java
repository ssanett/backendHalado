package training360.school.exceptions;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(long stdId) {
        super(String.format("Student not found with id: %d",stdId));
    }
}
