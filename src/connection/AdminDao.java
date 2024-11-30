package connection;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    void addDoctor(String doctorName, String dept) throws SQLException;
    boolean removeDoctor(int doctorId);
    void addDepartment(String departmentName);
    void removeDepartment(String departmentName);
    List<String> getDoctorsWithDepartments();
    List<String> getAllDepartments();
    boolean departmentExists(String departmentName);
    boolean doctorExists(int doctorId);
    int getDepartmentIdByName(String deptName);
    int getDepartmentIdById(int departmentId);
}
