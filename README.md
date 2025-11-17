# Patient Record Web Application - Security Assessment

This repository contains the source code for a patient record web application used in a security assessment assignment. The goal was to identify and fix common web application vulnerabilities affecting both the front-end and back-end, focusing on protecting sensitive patient data.

## Overview

The application is written in Java using:
- Jetty Web Server
- Java Servlets
- SQLite (via JDBC)
- FreeMarker template engine

Vulnerabilities were discovered through:
1. **Web interface testing** – e.g., missing HTTPS, weak session management, credentials transmitted in plaintext.
2. **Code and database review** – e.g., plaintext password storage, SQL injection vectors, improper error handling.

## Key Findings

- **Web Interface Vulnerabilities**
  - Missing server session cookies / weak session management
  - Credentials sent over HTTP (plaintext)
  - SQL injection vectors via login/search forms

- **Code / Database Vulnerabilities**
  - Plaintext password storage in the database
  - Lack of secure error handling and logging
  - Hardcoded SQL queries vulnerable to injection

## Fixes Implemented

1. **HTTPS Enforcement**
   - All HTTP traffic is redirected to HTTPS (Jetty configured with a self-signed certificate)
   - Session cookies are set with `Secure`, `HttpOnly`, and `SameSite` attributes
   - Prevents credential interception and man-in-the-middle attacks

2. **Password Hashing**
   - SHA-256 hashing implemented for password storage
   - Existing plaintext passwords converted to hashed values
   - Prevents unauthorized access even if the database is compromised

## Getting Started

1. Install **Java SE Development Kit 11.0.18** (or newer)
2. Clone the repository:
   ```bash
   git clone https://github.com/inemaa93/IKT222-G-25H-Identifying-and-Fixing-Security-Vulnerabilities-on-a-Web-Application.git
Navigate to the project folder and run:

bash
Kopier kode
./gradlew run   # or gradlew.bat run on Windows
Access the application:

HTTP requests on port 8080 will redirect to HTTPS on port 8443

Use https://localhost:8443 in your browser (expect a self-signed certificate warning)

Lessons Learned
Secure coding practices and OWASP guidelines are essential

Always use HTTPS for sensitive data

Hash passwords and avoid SQL query concatenation

Proper error handling and logging help with monitoring and incident response

---

This project was made with the help of ChatGPT.
