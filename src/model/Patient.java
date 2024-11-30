package model;

import java.time.LocalDate;


public class Patient {
    private String name;
    private String password;
    private String patientId;
    private String placeOfBirth;
    private String address;
    private String province;
    private String city;
    private String barangay;
    private LocalDate bday;
    private String contactNo;
    private String email;
    private String emergencyNo;
    private Gender gender;
    private String nationality;
    private String maritalStatus;
    private String allergy;
    private String past; // past surgeries or treatments
    private String bloodtype;
    private boolean hasDisability;
    private String disabilityDetails;
    private boolean isVaccinated;


    public enum Gender {
        Female, Male
    }

    public Patient(String patientId, String name, String password, String address, String province, String city, String barangay, String placeOfBirth, LocalDate bday, String contactNo, String email,
                   String emergencyNo, Gender gender, String nationality, String maritalStatus, String allergy, String past, String bloodtype, boolean hasDisability, boolean isVaccinated, String disabilityDetails) {
        this.name = name;
        this.password = password; 
        this.address = address;
        this.province =  province;
        this.city = city;
        this.barangay = barangay;
        this.bday = bday;
        this.placeOfBirth = placeOfBirth;
        this.contactNo = contactNo;
        this.email = email;
        this.emergencyNo = emergencyNo;
        this.gender = gender;
        this.nationality = nationality;
        this.maritalStatus =  maritalStatus;
        this.allergy = allergy;
        this.past = past;
        this.bloodtype = bloodtype;
        this.patientId = patientId;
        this.hasDisability = hasDisability;
        this.isVaccinated = isVaccinated;
        this.disabilityDetails = disabilityDetails;
    }

    public Patient(String patientId, String name, String password) {
        this.name = name;
        this.password = password; 
        this.patientId = patientId;
    }

    // Getters
    public String getUniqueId() { return patientId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public String getProvince() { return province; }
    public String getCity() { return city; }
    public String getBarangay() { return barangay; }
    public String getPlaceOfBirth() { return placeOfBirth; }
    public Gender getGender() { return gender; }
    public LocalDate getBday() { return bday; }
    public String getContactNo() { return contactNo; }
    public String getEmail() { return email; }
    public String getEmergencyNo() { return emergencyNo; }
    public String getNationality() { return nationality; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getAllergy() { return allergy; }
    public String getBloodtype() { return bloodtype; }
    public boolean isHasDisability() { return hasDisability; }
    public boolean isVaccinated() { return isVaccinated; }
    public String getDisabilityDetails() { return disabilityDetails; }
    public String getPast() { return past; }


    // Setters
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setAddress(String address) { this.address = address; }
    public void setProvince(String province) { this.province = province; }
    public void setCity(String city) { this.city = city; }
    public void setBarangay(String barangay) { this.barangay = barangay; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setBday(LocalDate bday) { this.bday = bday; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public void setEmail(String email) { this.email = email; }
    public void setEmergencyNo(String emergencyNo) { this.emergencyNo = emergencyNo; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
    public void setAllergy(String allergy) { this.allergy = allergy; }
    public void setPast(String past) { this.past = past; }
    public void setBloodtype(String bloodtype) { this.bloodtype = bloodtype; }
    public void setVaccinated(boolean isVaccinated) { this.isVaccinated = isVaccinated; }
    public void setHasDisability(boolean hasDisability) { this.hasDisability = hasDisability; }
    public void setDisabilityDetails(String disabilityDetails) { this.disabilityDetails = disabilityDetails; }

    public Patient(String name, String password) { 
        this.name = name;
        this.password = password;  
    }

    
}