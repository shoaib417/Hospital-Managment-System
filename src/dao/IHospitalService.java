package dao;

import entity.Appointment;

import java.util.List;

public interface IHospitalService {

    // Get appointment by ID
    Appointment getAppointmentById(int appointmentId);
    
    // Get appointments for a specific patient
    List<Appointment> getAppointmentsForPatient(int patientId);

    // Get appointments for a specific doctor
    List<Appointment> getAppointmentsForDoctor(int doctorId);

    // Schedule a new appointment
    boolean scheduleAppointment(Appointment appointment);

    // Update an existing appointment
    boolean updateAppointment(Appointment appointment);

    // Cancel an appointment by ID
    boolean cancelAppointment(int appointmentId);
}
