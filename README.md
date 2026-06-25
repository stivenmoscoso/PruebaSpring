# TalentBoard - Applicant Tracking System (ATS)

## Description
TalentBoard is a centralized platform designed to manage job vacancies, candidate profiles, applications, and interviews within a structured workflow. It addresses recruitment operational challenges such as double applications, lost vacancy tracking, and uncoordinated interview scheduling.

## Tech Stack
- **Java 21** & **Spring Boot 3.x**
- **Spring Data JPA** (Persistence layer)
- **Spring Security** & **JWT (JJWT 0.12.5)** (Role-based stateless authentication)
- **PostgreSQL** (Relational Database)
- **Docker** & **Docker Compose** (Containerization)
- **Lombok** (Boilerplate reduction)

## Core Business Rules Implemented
1. **No Past Interviews:** Interviews cannot be scheduled in dates prior to the current system date.
2. **Unique Application Constraint:** Candidates are restricted from applying more than once to the same job vacancy.
3. **Closed Vacancy Restriction:** Applications are automatically rejected if the target vacancy is marked as `CLOSED`.
4. **Role Isolation:** Endpoints are strictly isolated using Spring Security Filters (`ADMIN`, `RECRUITER`, and `CANDIDATE`).

## Environment Variables Required
The application uses the following default variables configured via Docker Compose:
- `SPRING_DATASOURCE_URL`: Connection string to the relational database.
- `SPRING_DATASOURCE_USERNAME`: Database administrative user.
- `SPRING_DATASOURCE_PASSWORD`: Database connection secret.
- `APP_JWT_SECRET`: Hexadecimal or cryptographic alphanumeric key used to sign the tokens.

## Installation & Execution Guide

### Prerequisites
- Docker and Docker Compose installed.
- Git.

### Step-by-Step Execution
1. Clone the repository:
   bash
   git clone https://github.com/stivenmoscoso/PruebaSpring

                                                                                                                                                                                                                               
