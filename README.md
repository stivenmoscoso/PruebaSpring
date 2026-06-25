# TalentBoard - Applicant Tracking & Recruitment Management System

## 📝 Project Description
TalentBoard is a centralized, production-ready Applicant Tracking System (ATS) designed to streamline organizational recruitment processes. It eliminates fragmented workflows—such as disconnected spreadsheets, emails, and shared documents—by unifying vacancy management, candidate registration, job applications, and interview scheduling into a single, cohesive platform.

The system enforces strict business rules, such as preventing duplicate candidate applications on the same vacancy, restricting applications to open positions only, ensuring interview schedules are not set in past dates, and isolating data access controls via role-based security (Admin, Recruiter, and Candidate).

---

## 🛠️ Technologies & Ecosystem
* **Backend Framework:** Java 17 & Spring Boot 3.x
* **Security & Authentication:** Spring Security & Stateless JSON Web Tokens (JWT)
* **Data Persistence:** Spring Data JPA & Hibernate
* **Database:** PostgreSQL / MySQL (Production-ready relational storage)
* **Data Validation:** Jakarta Validation API (`@Valid`, `@NotBlank`, `@Positive`)
* **Code Optimization:** Project Lombok
* **Containerization & Deployment:** Docker & Docker Compose

---

## ⚙️ Environment Variables
The application relies on environment variables to externalize configuration data secure and dynamically. Make sure the following keys are set before running the application:

| Variable Name | Description | Example Value |
| --- | --- | --- |
| `DB_URL` | JDBC Connection URL string | `jdbc:postgresql://db:5432/talentboard` |
| `DB_USERNAME` | Database administrator username | `postgres` |
| `DB_PASSWORD` | Database administrator password | `your_secure_password` |
| `JWT_SECRET` | 256-bit fixed cryptographic secret key | `3c9634D2A4B627164763149524