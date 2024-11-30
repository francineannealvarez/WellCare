package model;



public class Doctor { 
    private String doctorId;
    private String name;
    private String department;


    
    public Doctor(String doctorId, String name, String department) {
        this.doctorId = doctorId;
        this.name = name;
        this.department = department;    
        }



    public String getName(){
        return name;
    }
 
    public String getDoctorId() {
        return doctorId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
