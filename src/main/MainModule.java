package main;
import dao.*;
import entity.*;
import myexceptions.*;

import java.util.*;
public class MainModule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IHospitalService hospitalService = new HospitalServiceImpl();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Hospital Management System Menu:");
            System.out.println("1. Get Appointment by ID");
            System.out.println("2. Get Appointments for Patient");
            System.out.println("3. Get Appointments for Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Update Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Appointment ID: ");
                    int appointmentId = scanner.nextInt();
                    Appointment appointmentById = hospitalService.getAppointmentById(appointmentId);
                    if(appointmentById==null)
                    {
                        System.out.println("No appointment details found");

                    }
                    else
                    {
                        System.out.println("Appointment details: " + appointmentById);
                    }
                    break;

                case 2:
                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    try
                    {
                    	List<Appointment> appointmentsForPatient = hospitalService.getAppointmentsForPatient(patientId);
                        System.out.println("Appointments for Patient: " + appointmentsForPatient);
                    }
                    catch(PatientNumberNotFoundException e)
                    {
                    	System.out.println("Patient ID is invalid or not present in database");
                    }
                    break;

                case 3:
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = scanner.nextInt();
                    try
                    {
                        List<Appointment> appointmentsForDoctor = hospitalService.getAppointmentsForDoctor(doctorId);
                        System.out.println("Appointments for Doctor: " + appointmentsForDoctor);
                    }
                    catch(DoctorNumberNotFoundException e)
                    {
                    	System.out.println("Doctor ID is invalid or not present in database");
                    }
                    break;

                case 4:
                	System.out.println("Enter Appointment details:");
                    System.out.print("Patient ID: ");
                    int patientIdForSchedule = scanner.nextInt();
                    System.out.print("Doctor ID: ");
                    int doctorIdForSchedule = scanner.nextInt();
                    System.out.print("Appointment Date (YYYY-MM-DD): ");
                    String appointmentDateForSchedule = scanner.next();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Description: ");
                    String descriptionForSchedule = scanner.nextLine();

                    Appointment newAppointment = new Appointment(0, patientIdForSchedule, doctorIdForSchedule, appointmentDateForSchedule, descriptionForSchedule);
                    try
                    {
                    	boolean isScheduled = hospitalService.scheduleAppointment(newAppointment);
                        if(isScheduled)
                        {
                            System.out.println("Appointment scheduled successfully");
                        }
                        else
                        {
                            System.out.println("Failed to schedule appointment");

                        }
                    }
                    catch(DoctorNumberNotFoundException e)
                    {
                    	System.out.println("Doctor ID is invalid or not present in database");
                    }
                    catch(PatientNumberNotFoundException e)
                    {
                    	System.out.println("Patient ID is invalid or not present in database");
                    }
                    
                    break;

                case 5:
                	System.out.println("Enter Updated Appointment details:");
                    System.out.print("Appointment ID to update: ");
                    int appointmentIdForUpdate = scanner.nextInt();
                    System.out.print("New Appointment Date (YYYY-MM-DD): ");
                    String newAppointmentDate = scanner.next();
                    scanner.nextLine();
                    System.out.print("New Description: ");
                    String newDescription = scanner.nextLine();
                    Appointment updatedAppointment = new Appointment(appointmentIdForUpdate, 0, 0, newAppointmentDate, newDescription);
                    try
                    {
                    	boolean isUpdated = hospitalService.updateAppointment(updatedAppointment);
                        if(isUpdated)
                        {
                            System.out.println("Appointment updated successfully");
                        }
                        else
                        {
                            System.out.println("Failed to update appointment");

                        }
                    }
                    catch(AppointmentNumberNotFoundException e)
                    {
                    	System.out.println("Appointment ID is invalid or not present in database");
                    }

                    break;

                case 6:
                    System.out.print("Enter Appointment ID to cancel: ");
                    int appointmentIdToCancel = scanner.nextInt();
                    try
                    {
                    	boolean isCanceled = hospitalService.cancelAppointment(appointmentIdToCancel);
                        if(isCanceled)
                        {
                        	System.out.println("Appointment canceled successfully.");
                        }
                        else
                        {
                        	System.out.println("Failed to cancel appointment.");
                        }
                    }catch(AppointmentNumberNotFoundException e)
                    {
                    	System.out.println("No Appointment Exist For Appointment Number "+appointmentIdToCancel);
                    }
                    
                    break;

                case 0:
                    System.out.println("Exiting the system. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
	}

}
