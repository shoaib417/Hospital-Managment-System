package myexceptions;

public class DoctorNumberNotFoundException extends RuntimeException {
	public DoctorNumberNotFoundException(String message) {
        super(message);
    }
}
