package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoJdbc implements AdminDao {
    private Connection connection;

    public AdminDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    public void addDoctor(String doctorName, String deptName) throws SQLException {
        if (connection == null) {
            System.out.println("Database connection is null.");
            return;
        }
    
        if (!departmentExists(deptName)) {
            System.out.println("Department " + deptName + " does not exist. Cannot add doctor.");
            return; 
        }
    
        int departmentId = getDepartmentIdByName(deptName);
    
        if (departmentId == -1) {
            System.out.println("Department name not found. Please ensure the department exists.");
            return;
        }
    
        String doctorId = generateUniqueId();
        String query = "INSERT INTO doctor (Doctor_ID, Doctor_Name, Department_ID, employment_status) VALUES (?, ?, ?, ?)";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, doctorId); 
            stmt.setString(2, doctorName);
            stmt.setInt(3, departmentId);
            stmt.setString(4, "active"); 
    
            int rowsAffected = stmt.executeUpdate(); 
            if (rowsAffected > 0) {
                System.out.println("Doctor " + doctorName + " added successfully to the " + deptName + " department with ID: " + doctorId);
                insertDefaultAvailableTimes(doctorId);
            } else {
                System.out.println("No rows affected. Check if the data is correct.");
            }
    
        } catch (SQLException e) {
            System.out.println("Error while inserting doctor: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    private String generateUniqueId() throws SQLException {
        int year = java.time.LocalDate.now().getYear(); 
        String query = "SELECT COUNT(*) FROM doctor WHERE Doctor_ID LIKE ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, year + "%");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1); 
            return year + String.format("%05d", count + 1);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
    
        return null; 
    }
        
    private void insertDefaultAvailableTimes(String doctorId) {
        String checkQuery = "SELECT COUNT(*) FROM available_times WHERE Doctor_ID = ?";
        
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, doctorId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1); 
    
            if (count == 0) {  
                String query = "INSERT INTO available_times (Doctor_ID, Available_Time) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    String[] defaultTimes = {
                        "2024-12-24 10:00 AM",
                        "2024-12-24 02:00 PM",
                        "2024-12-25 10:00 AM",
                        "2024-12-25 02:00 PM"
                    };
    
                    for (String time : defaultTimes) {
                        stmt.setString(1, doctorId); 
                        stmt.setString(2, time);  
                        stmt.executeUpdate();  
                    }
                    //System.out.println("Default available times inserted for doctor with ID: " + doctorId); for debugging
                }
            } else {
                System.out.println("Doctor already has available times. Skipping insertion.");
            }
        } catch (SQLException e) {
            System.out.println("Error while checking or inserting available times: " + e.getMessage());
        }
    }
    
    @Override
    public boolean removeDoctor(int doctorID) {
        if (!doctorExists(doctorID)) {
            System.out.println("Doctor with ID " + doctorID + " does not exist.");
            return false;
        }

        String updateDoctorQuery = "UPDATE doctor SET employment_status = 'no_longer_employed' WHERE Doctor_ID = ?";
        String cancelAppointmentsQuery = "UPDATE appointments SET Status = 'CANCELED' WHERE Doctor_ID = ?";
        String moveToCancelledAppointmentsQuery = "INSERT INTO canceled_appointments (Appointment_ID, Doctor_ID, Patient_ID, Department_ID, Cancel_Reason) " +
                                                "SELECT Appointment_ID, Doctor_ID, Patient_ID, Department_ID, 'Doctor is no longer employed.' " +
                                                "FROM appointments WHERE Doctor_ID = ? AND Status = 'CANCELED'";

        try (PreparedStatement updateDoctorStmt = connection.prepareStatement(updateDoctorQuery);
            PreparedStatement cancelAppointmentsStmt = connection.prepareStatement(cancelAppointmentsQuery);
            PreparedStatement moveToCancelledAppointmentsStmt = connection.prepareStatement(moveToCancelledAppointmentsQuery)) {

            // Update doctor status to 'no_longer_employed'
            updateDoctorStmt.setInt(1, doctorID);
            int doctorRowsAffected = updateDoctorStmt.executeUpdate();

            if (doctorRowsAffected > 0) {
                // Cancel all appointments for the doctor
                cancelAppointmentsStmt.setInt(1, doctorID);
                int appointmentRowsAffected = cancelAppointmentsStmt.executeUpdate();

                if (appointmentRowsAffected > 0) {
                    // Move canceled appointments to the cancelled_appointments table
                    moveToCancelledAppointmentsStmt.setInt(1, doctorID);
                    int cancelledRowsInserted = moveToCancelledAppointmentsStmt.executeUpdate();

                    System.out.println("Doctor with ID " + doctorID + " has been removed, " + appointmentRowsAffected +
                                    " appointments were cancelled, and " + cancelledRowsInserted + " were logged in cancelled_appointments.");
                } else {
                    System.out.println("Doctor with ID " + doctorID + " has been removed, but no appointments were found to cancel.");
                }
                return true;
            } else {
                System.out.println("Failed to update doctor status. No rows affected.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error while updating doctor status or cancelling appointments: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doctorExists(int doctorId) {
        String query = "SELECT COUNT(*) FROM doctor WHERE Doctor_ID = ? AND employment_status = 'active'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;  
        } catch (SQLException e) {
            System.out.println("Error checking doctor existence: " + e.getMessage());
            //e.printStackTrace();
        }
        return false; 
    }

    @Override
    public void addDepartment(String departmentName) {
        if (departmentExists(departmentName)) {
            System.out.println("Department '" + departmentName + "' already exists.");
            return;
        }

        if (inactiveDepartmentExists(departmentName)) {
            String reactivateQuery = "UPDATE department SET is_active = 1 WHERE Department_Name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(reactivateQuery)) {
                stmt.setString(1, departmentName);
                stmt.executeUpdate();
                //System.out.println("Department '" + departmentName + "' reactivated successfully."); (for debugging)
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                //e.printStackTrace();
            }
            return;
        }

        String insertQuery = "INSERT INTO department (Department_Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setString(1, departmentName);
            stmt.executeUpdate();
            System.out.println("Department added successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    @Override
    public boolean departmentExists(String departmentName) {
        String query = "SELECT COUNT(*) FROM department WHERE Department_Name = ? AND is_active = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, departmentName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }

        } catch (SQLSyntaxErrorException e) {
            System.err.println("Table 'department' does not exist. Please initialize the database properly.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
        return false;
    }

    public boolean inactiveDepartmentExists(String departmentName) {
        String query = "SELECT COUNT(*) FROM department WHERE Department_Name = ? AND is_active = 0";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, departmentName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Table 'department' does not exist. Please initialize the database properly.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeDepartment(String departmentName) {
        if (!departmentExists(departmentName)) {
            System.out.println("Department '" + departmentName + "' does not exist or is already inactive.");
            return;
        }
        //Instead of deleting the department from the database, its status will simply be updated.
        String query = "UPDATE department SET is_active = 0 WHERE Department_Name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, departmentName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Department '" + departmentName + "' deleted successfully.");
            } else {
                System.out.println("No department found with that name.");
            }
        } catch (SQLException e) {
            System.out.println("Error while marking department as inactive: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    @Override
    public List<String> getDoctorsWithDepartments() {
        List<String> doctorsWithDepartments = new ArrayList<>();
        String query = "SELECT doctor.Doctor_ID, doctor.Doctor_Name, department.Department_ID, department.Department_Name " +
                    "FROM doctor " +
                    "JOIN department ON doctor.Department_ID = department.Department_ID " +
                    "WHERE department.is_active = 1 AND doctor.employment_status = 'active'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String doctorInfo = "Doctor ID: " + resultSet.getInt("Doctor_ID") + ", Name: " + 
                                    resultSet.getString("Doctor_Name") + ", Dept ID: " + 
                                    resultSet.getInt("Department_ID") + ", Dept Name: " + 
                                    resultSet.getString("Department_Name");
                doctorsWithDepartments.add(doctorInfo);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
        return doctorsWithDepartments;
    }

    @Override
    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<>();

        String query = "SELECT Department_ID, Department_Name FROM department WHERE is_active = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String departmentInfo = "Department ID: " + resultSet.getInt("Department_ID") + 
                                        ", Department Name: " + resultSet.getString("Department_Name");
                departments.add(departmentInfo);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
        return departments;
    }

    @Override
    public int getDepartmentIdById(int departmentId) {
        String sql = "SELECT Department_ID FROM department WHERE Department_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Department_ID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getDepartmentIdByName(String deptName) {
        //System.out.println("Fetching Department ID for: " + deptName); (for debugging)

        String sql = "SELECT Department_ID FROM department WHERE Department_Name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, deptName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Department_ID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        }
        return -1; 
    }

}
   
