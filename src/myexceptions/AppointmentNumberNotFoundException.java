package myexceptions;

public class AppointmentNumberNotFoundException extends RuntimeException {
	public AppointmentNumberNotFoundException(String message) {
        super(message);
    }
}
