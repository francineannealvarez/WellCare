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

## MySQL Integration 💾

This project integrates a MySQL database to manage and store critical hospital booking system data. The integration ensures data persistence and efficient handling of:

- **Patient Information**: Stores personal details, login credentials, and medical histories.
- **Doctor Information**: Includes doctor profiles, associated departments, and available time slots.
- **Departments**: Maintains a list of hospital departments, allowing for dynamic additions and updates.
- **Appointments**: Tracks the status of appointments, categorized as scheduled, canceled, or completed.
- **Canceled Appointments**: Stores detailed records, including cancellation reasons, for patient review.
- **Medical History**: Preserves patient medical histories, diagnoses, and treatments for seamless access.

The MySQL integration ensures robust and secure data storage, supporting functionalities such as patient login, doctor schedule management, appointment booking, and medical history retrieval.


🍏 **Health Tips Section**: A dedicated section of the system provides helpful health tips for all users, not just registered patients. These tips cover a variety of health-related topics, such as healthy eating and dietary recommendations. The system includes multiple categories such as:

- **For Low Blood Pressure**
- **For High Blood Pressure**
- **For Weight Loss**
- **For Boosting Immune System**

Users can explore health tips and apply them to enhance their well-being.

# Application of OOP Principles

## Encapsulation 🔒
- The project encapsulates the attributes and behaviors of key entities like the `Doctor`, `Patient`, `Appointment`, `MedicalHistory`, and `CancelledAppointment` classes. These classes have private fields that are accessible only through getter and setter methods, ensuring controlled access and modification of the object states.

- For instance, in the `Patient` class, the `medicalHistory` field is private, and it can only be accessed or modified through specific methods like `getMedicalHistory()` and `addMedicalHistory()`. This ensures that the medical data remains secure and consistent, preventing unauthorized modifications.

---

## Abstraction 💡
- Abstraction is applied to hide the complex details of database interactions. The DAO interfaces (e.g., `PatientDAO`, `DoctorDAO`, `AdminDAO`) define high-level methods that abstract away the specific implementation of database operations. The actual database interactions are handled by the `DAOJdbc` classes, which implement these interfaces. 

- For example, the `PatientDAOJdbc` implements the methods defined in the `PatientDAO` interface, handling operations like adding a patient, updating patient details, or fetching patient records from the database. This abstracts away the database-related complexity from the classes that use these methods, such as the `Admin` or `Doctor` class, so they don’t need to worry about the specific implementation details of the database interactions. The user interface remains simple and clean, as these classes interact with the high-level methods defined in the DAO interfaces, which hide the complexities of actual database queries.

- Similarly, the `HealthTips` abstract class defines abstract methods that must be implemented by its subclasses. The subclasses, such as `LowBloodTips` and `HighBloodTips`, provide their own specific implementations by overriding the abstract methods. 

- By using abstraction, the project ensures that the user only interacts with the relevant information and does not need to deal with unnecessary implementation details.

---

## Inheritance 🧬
- The project employs inheritance through the `User` class, which serves as a superclass for the `Admin` class. This allows the `Admin` class to inherit common attributes such as `name` and `password`, as well as methods like `signIn()`. 

- The inheritance structure helps eliminate redundancy and ensures that any functionality common to both the `Admin` and `User` classes is maintained in one central location.

- Additionally, the `HealthTips` class is an abstract class that serves as a superclass. Its subclasses, such as `LowBloodTips` and `HighBloodTips`, inherit and override methods to provide specific functionality based on the condition being addressed. This allows for code reuse and a clear structure for health tips tailored to specific health conditions. For instance, a `LowBloodTips` subclass might implement a method to provide dietary tips for those with low blood pressure, while the `HighBloodTips` subclass might implement similar functionality for those with high blood pressure.

---

## Polymorphism 🔄
- Polymorphism is used throughout the project, especially in the DAO classes. The methods defined in the DAO interfaces (e.g., `PatientDAO`, `DoctorDAO`, `AdminDAO`) are implemented in the corresponding `DAOJdbc` classes (e.g., `PatientDAOJdbc`, `DoctorDAOJdbc`). 

- This allows the `Admin`, `Doctor`, and `Patient` classes to interact with the database without needing to know the exact implementation details. For example, a `PatientDAO` can be used interchangeably with its implementation `PatientDAOJdbc`, and the interaction with the database will still work correctly, regardless of which specific DAOJdbc class is used.

- Polymorphism is demonstrated when the interfaces are used in classes like the `DoctorPanel` and `PatientPanel`, where they reference the DAO interface (e.g., `PatientDAO patientDAO = new PatientDAOJdbc()`) and call the respective methods. This provides flexibility, as different DAO implementations can be swapped out seamlessly. For example, one could swap `DAOJdbc` for a `DAOHibernate` implementation, and the rest of the system would still function properly due to the polymorphic nature of the code.

---

