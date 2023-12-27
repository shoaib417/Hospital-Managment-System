package dao;
import java.sql.*;
import entity.Appointment;
import util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import myexceptions.*;
public class HospitalServiceImpl implements IHospitalService {

	static Connection con=null;
	public HospitalServiceImpl()
	{
//		String prop=PropertyUtil.getPropertySt/ring("./src/main/database.properties");
		con=DBConnection.getConnection();
//		con=DBConnection.getConnection(prop);
	}

    @Override
    public Appointment getAppointmentById(int appointmentId) {
    	String query = "SELECT * FROM Appointment WHERE appointmentId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, appointmentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Appointment(
                            resultSet.getInt("appointmentId"),
                            resultSet.getInt("patientId"),
                            resultSet.getInt("doctorId"),
                            resultSet.getString("appointmentDate"),
                            resultSet.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
    	boolean check=checkPatientIDExist(patientId);
    	if(check==false)
    	{
    		throw new PatientNumberNotFoundException("Patient ID is invalid or not present in database");
    	}
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointment WHERE patientId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment(
                            resultSet.getInt("appointmentId"),
                            resultSet.getInt("patientId"),
                            resultSet.getInt("doctorId"),
                            resultSet.getString("appointmentDate"),
                            resultSet.getString("description")
                    );
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
    	boolean check=checkDoctorIDExist(doctorId);
    	if(check==false)
    	{
    		throw new DoctorNumberNotFoundException("Doctor ID is invalid or not present in database");
    	}
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointment WHERE doctorId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment(
                            resultSet.getInt("appointmentId"),
                            resultSet.getInt("patientId"),
                            resultSet.getInt("doctorId"),
                            resultSet.getString("appointmentDate"),
                            resultSet.getString("description")
                    );
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
    	boolean checkDoc=checkDoctorIDExist(appointment.getDoctorId());
    	if(checkDoc==false)
    	{
    		throw new PatientNumberNotFoundException("Doctor ID is invalid or not present in database");
    	}
    	boolean checkPat=checkPatientIDExist(appointment.getPatientId());
    	if(checkPat==false)
    	{
    		throw new PatientNumberNotFoundException("Patient ID is invalid or not present in database");
    	}
    	String query = "INSERT INTO Appointment (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, appointment.getPatientId());
            preparedStatement.setInt(2, appointment.getDoctorId());
            preparedStatement.setString(3, appointment.getAppointmentDate());
            preparedStatement.setString(4, appointment.getDescription());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
    	boolean checkApp=checkAppointmentIDExist(appointment.getAppointmentId());
    	if(checkApp==false)
    	{
    		throw new AppointmentNumberNotFoundException("Appointment ID is invalid or not present in database");
    	}
    	String query = "UPDATE Appointment SET appointmentDate = ?, description = ? WHERE appointmentId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, appointment.getAppointmentDate());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setInt(3, appointment.getAppointmentId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
    	boolean checkApp=checkAppointmentIDExist(appointmentId);
    	if(checkApp==false)
    	{
    		throw new AppointmentNumberNotFoundException("Appointment ID is invalid or not present in database");
    	}
    	String query = "DELETE FROM Appointment WHERE appointmentId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, appointmentId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }
    
    public static boolean checkPatientIDExist(int patientID)
    {
    	String query = "SELECT COUNT(*) FROM Patient WHERE patientId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, patientID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public static boolean checkDoctorIDExist(int doctorID)
    {
    	String query = "SELECT COUNT(*) FROM Doctor WHERE doctorId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean checkAppointmentIDExist(int appointmentID)
    {
    	String query = "SELECT COUNT(*) FROM Appointment WHERE appointmentId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1,appointmentID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
