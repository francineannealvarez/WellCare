# **WellCare: Hospital Booking System 🏥**

## **I. Project Overview 🌟**

**WellCare: Hospital Booking System** is a Java-based application designed to simplify the management of patient appointments, doctor schedules, and medical histories within a hospital setting. The system aims to streamline interactions between patients, doctors, and administrators, making healthcare management more efficient and accessible.

Through the application, patients can book appointments, view medical histories, and explore health tips, while doctors manage their schedules and provide diagnoses. The system also includes a health tips section that is not limited to patients but is available to all users for improving their health and lifestyle.

Built using Object-Oriented Programming (OOP) principles, **WellCare** offers an intuitive interface and leverages the power of abstraction, inheritance, polymorphism, and encapsulation to deliver a robust, maintainable, and scalable application.

 ##**Key Features ✨**

🔑 **Patient Registration & Login**: Secure patient sign-in functionality allows patients to create and manage their profiles. Once logged in, they can view appointment histories, make new bookings, and manage their details.

🗓️ **Appointment Booking**: Patients can easily browse available departments and schedule appointments with doctors based on their availability.

📜 **View Medical History**: Patients can access a detailed record of their previous appointments, diagnoses, and treatments, allowing them to stay informed about their healthcare journey.

👨‍⚕️ **Doctor Schedule Management**: Doctors can set, update, and view their availability for appointments. This feature allows doctors to manage their daily schedules efficiently and ensure patients book at available times.

❌ **Appointment Cancellation**: Doctors have the ability to cancel appointments with an optional reason, which will be visible to the patients, ensuring transparency.

🛠️ **Admin Features**: Admins have full control over the system, including the ability to add or remove doctors and departments. This feature helps manage and update the hospital booking system as needed.

🍏 **Health Tips Section**: A dedicated section of the system provides helpful health tips for all users, not just registered patients. These tips cover a variety of health-related topics, such as healthy eating and dietary recommendations. The system includes multiple categories such as:

- **For Low Blood Pressure**
- **For High Blood Pressure**
- **For Weight Loss**
- **For Boosting Immune System**

Users can explore health tips and apply them to enhance their well-being.

## MySQL Integration 💾

This project integrates a MySQL database to manage and store critical hospital booking system data. The integration ensures data persistence and efficient handling of:

- **Patient Information**: Stores personal details, login credentials, and medical histories.
- **Doctor Information**: Includes doctor profiles, associated departments, and available time slots.
- **Departments**: Maintains a list of hospital departments, allowing for dynamic additions and updates.
- **Appointments**: Tracks the status of appointments, categorized as scheduled, canceled, or completed.
- **Canceled Appointments**: Stores detailed records, including cancellation reasons, for patient review.
- **Medical History**: Preserves patient medical histories, diagnoses, and treatments for seamless access.

The MySQL integration ensures robust and secure data storage, supporting functionalities such as patient login, doctor schedule management, appointment booking, and medical history retrieval.

---

# **II. Application of OOP Principles 🖥️💡**

## Encapsulation 🔒
- The project uses encapsulation to protect the attributes and behaviors of key entities like the `Doctor`, `Patient`, `Appointment`, `MedicalHistory`, and `CancelledAppointment` classes. These classes have private fields that are only accessible through getter and setter methods, ensuring controlled access and modification of object states.

- For example, in the `Patient` class, the `medicalHistory` field is private. It can only be accessed or modified through methods like `getMedicalHistory()` and `addMedicalHistory()`. This ensures that medical data remains secure and consistent, preventing unauthorized modifications.

---

## Abstraction 💡
- Abstraction hides the complexity of database interactions. The DAO interfaces (e.g., `PatientDAO`, `DoctorDAO`, `AdminDAO`) define high-level methods that abstract away the specific database operations. The actual database interactions are handled by classes like `DAOJdbc`, which implement these interfaces.

- For example, the `PatientDAOJdbc` implements the methods defined in the `PatientDAO` interface, handling operations like adding a patient, updating patient details, or fetching patient records from the database. This abstracts away the database-related complexity from the classes that use these methods, such as the `Admin` or `Doctor` class, so they don’t need to worry about the specific implementation details of the database interactions. The user interface remains simple and clean, as these classes interact with the high-level methods defined in the DAO interfaces, which hide the complexities of actual database queries.

- Similarly, the `HealthTips` abstract class defines abstract methods that must be implemented by its subclasses. The subclasses, such as `LowBloodTips` and `HighBloodTips`, provide their own specific implementations by overriding the abstract methods. 

- By using abstraction, the project ensures that the user only interacts with the relevant information and does not need to deal with unnecessary implementation details.

---

## Inheritance 🧬
- The project employs inheritance through the `User` class, which serves as a superclass for the `Admin` class. This allows the `Admin` class to inherit common attributes such as `name` and `password`, as well as method `signIn()`. This inheritance structure reduces redundancy and ensures that shared functionality remains centralized.

- Additionally, the `HealthTips` class is an abstract class that serves as a superclass. Its subclasses, such as `LowBloodTips` and `HighBloodTips`, inherit and override methods to provide specific functionality based on the condition being addressed. This allows for code reuse and a clear structure for health tips tailored to specific health conditions. For instance, a `LowBloodTips` subclass might implement a method to provide dietary tips for those with low blood pressure, while the `HighBloodTips` subclass might implement similar functionality for those with high blood pressure.

---

## Polymorphism 🔄
- Polymorphism is applied throughout the project, particularly in the DAO classes. The methods defined in the DAO interfaces (e.g., `PatientDAO`, `DoctorDAO`, `AdminDAO`) are implemented by their respective `DAOJdbc` classes (e.g., `PatientDAOJdbc`, `DoctorDAOJdbc`).

- This enables flexibility, allowing `Admin`, `Doctor`, and `Patient` classes to interact with the database without knowing the specific implementation details. For example, the `PatientDAO` interface can be used with `PatientDAOJdbc`, and the system will function correctly without needing to modify the code if a new DAO implementation is introduced.

- Polymorphism is also demonstrated in classes like `DoctorPanel` and `PatientPanel`, where the DAO interfaces are referenced (e.g., `PatientDAO patientDAO = new PatientDAOJdbc()`), ensuring that different DAO implementations can be swapped seamlessly.

---

## **III. Integration with the Sustainable Development Goals (SDGs) 🌍**

This project supports **SDG 3: Good Health and Well-being**, focusing on improving access to healthcare and promoting preventive health measures.

- **Enhanced Healthcare Access** 🏥  
  Patients can easily book appointments with doctors, ensuring timely access to medical care.

- **Preventive Health Tips** 🍏  
  The HealthTips section provides valuable health advice, particularly regarding food, benefiting both patients and non-patients.

- **Efficient Health Information Management** 📊  
  Doctors can manage patient histories and diagnoses, ensuring that critical health information is accessible for informed decision-making.

- **Empowering Patients with Health History** 🩺  
  Patients can easily view their medical history, giving them a clearer understanding of their health status and previous treatments. This feature can aid in future diagnoses and the appropriate prescription of medicines.

By integrating these features, the project aligns with SDG 3 by making healthcare services and health information more accessible, promoting informed decision-making, and supporting overall well-being.

---

## **IV. Instructions for Running the Program 🖥️**

### Setup Instructions 🛠️

1. **📥 Clone the Repository:**
   - Clone the repository using Git:
   ```bash
   git clone https://github.com/your-username/WellCare.git
   cd WellCare

2. **🗄️ Install MySQL:**
  - Download and install MySQL.
  - Create a new MySQL database to be used with the project.
  - 
3. **⚙️ Import the SQL Schema:**
  - Import the provided SQL schema file (e.g., hospital_system.sql) into your MySQL database using MySQL Workbench or another MySQL client.
  - Open MySQL Workbench and connect to your MySQL server.
  - Open the SQL script and run it to create the necessary tables.

4. **⚙️ Download and Set Up MySQL Connector:**

  - Download the MySQL Connector JAR file, which is required to connect the Java program to your MySQL database.
  - You can download it from [here](https://dev.mysql.com/downloads/connector/j/).
  - Once downloaded, **place the `mysql-connector-j-9.1.0.jar` file in the `lib/` folder** of your project directory.

   Example directory structure:
   ```
  ├── lib/                           # Library files
  │   └── mysql-connector-j-9.1.0.jar # MySQL Connector JAR file
  ```

5. **⚙️ Update Database Configuration:**
   - Open the `DatabaseConnection` class in your codebase.
   - Replace the placeholder values with your MySQL credentials.
   
     Example:
     ```java
     String url = "jdbc:mysql://localhost:3306/your_database_name";
     String username = "your_username";
     String password = "your_password";
     ```

6. **🖥️ Install JDK:**
   - Download and install [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
   - Make sure Java is properly installed by running the following command in your terminal:
     ```bash
     java -version
     ```
     
7. **🔧 Install Visual Studio Code (VS Code):**
   - Download and install [Visual Studio Code](https://code.visualstudio.com/).

8. **⚙️ Install Java Extension Pack for VS Code:**
   - Open Visual Studio Code.
   - Go to Extensions (Ctrl+Shift+X) and search for "Java Extension Pack" by Microsoft. Install it to enable Java support.

### Running the Program in Visual Studio Code 🚀

1. **Open the Project in Visual Studio Code:**
   - Launch Visual Studio Code and open the project folder by selecting `File > Open Folder` and navigating to your project directory.

2. **Compile the Program:**
   - Open the integrated terminal in Visual Studio Code (Ctrl + `).
   - In the root directory of the project, compile the program with the MySQL Connector JAR in the classpath:
     ```bash
     javac -d out -cp lib/mysql-connector-java-9.1.0.jar $(find src -name "*.java")
     ```

3. **Run the Program:**
   - After successful compilation, run the program using the following command:
     ```bash
     java -cp out:lib/mysql-connector-java-9.1.0.jar main.Main
     ```
---
## Project File Structure 📁

Here is the structure of the project:

```
├── lib/                           # Library files
│   └── mysql-connector-j-9.1.0.jar # MySQL Connector JAR file
├── sql/                           # SQL files
│   └── WellCare.sql               # SQL schema file
├── src/                           # Java source files
│   ├── Main/                      # Main application classes
│   │   └── Main.java
│   ├── admin/                     # Admin-related classes
│   │   └── Admin.java
│   ├── connection/                # Database connection and DAOs
│   │   ├── AdminDao.java
│   │   ├── AdminDaoJdbc.java
│   │   ├── AppointmentDAO.java
│   │   ├── AppointmentDaoJdbc.java
│   │   ├── DatabaseConnection.java
│   │   ├── DoctorDao.java
│   │   ├── DoctorDaoJdbc.java
│   │   ├── PatientDao.java
│   │   └── PatientDaoJdbc.java
│   ├── display/                   # Display-related classes
│   │   ├── AppointmentTableDisplay.java
│   │   ├── CanceledAppointmentTableDisplay.java
│   │   ├── DisplayUtils.java
│   │   ├── DoctorScheduleTableDisplay.java
│   │   ├── MedicalHistoryTableDisplay.java
│   │   └── TableDisplay.java
│   ├── healthtips/                # Health tips-related classes
│   │   ├── BoostImmunityTips.java
│   │   ├── DietingTips.java
│   │   ├── HealthTips.java
│   │   ├── HighBloodTips.java
│   │   └── LowBloodTips.java
│   ├── model/                     # Core model classes (e.g., Appointment, Patient)
│   │   ├── Appointment.java
│   │   ├── AvailableTime.java
│   │   ├── CanceledAppointment.java
│   │   ├── MedicalHistory.java
│   │   └── Patient.java
│   ├── panel/                     # Panels for user interfaces
│   │   ├── DoctorPanel.java
│   │   └── PatientPanel.java
│   └── user/                      # User-related classes
│       └── User.java
└── README.md                      # Project documentation
```




