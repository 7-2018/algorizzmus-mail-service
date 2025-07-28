# Algorizzmus Mail Service

A microservice for sending transactional emails for the Algorizzmus authentication system. This service provides API endpoints for sending account verification and password reset emails.

## Features

- Send account verification emails with verification codes
- Send password reset emails with reset codes
- HTML email templates with responsive design
- Fallback to plain text emails if template processing fails

## Technologies

- Kotlin
- Spring Boot
- Spring Mail (JavaMailSender)
- Thymeleaf (for email templates)

## Prerequisites

- JDK 17 or higher
- Gradle
- SMTP server access (Gmail account configured for this example)

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/algorizzmus-mail-service.git
cd algorizzmus-mail-service
```

### 2. Configure environment variables

Create a `.env` file in the root directory based on the provided `.env.example`:

```
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

**Note for Gmail users:** You need to use an App Password instead of your regular password. To generate an App Password:
1. Enable 2-Step Verification on your Google Account
2. Go to [App Passwords](https://myaccount.google.com/apppasswords)
3. Select "Mail" and your device
4. Copy the generated password

### 3. Build the application

```bash
./gradlew build
```

### 4. Run the application

```bash
./gradlew bootRun
```

The service will start on port 8081 by default.

## API Endpoints

### Send Verification Email

Sends an email with a verification code for account activation.

**Endpoint:** `POST /send-verification-email`

**Request Body:**
```json
{
  "email": "user@example.com",
  "code": "123456",
  "username": "username"
}
```

**Response:**
- `200 OK` - Email sent successfully
- `400 Bad Request` - Invalid request
- `500 Internal Server Error` - Failed to send email

### Send Password Reset Email

Sends an email with a code for password reset.

**Endpoint:** `POST /send-forgot-password-email`

**Request Body:**
```json
{
  "email": "user@example.com",
  "code": "123456",
  "username": "username"
}
```

**Response:**
- `200 OK` - Email sent successfully
- `400 Bad Request` - Invalid request
- `500 Internal Server Error` - Failed to send email

## Email Templates

The service includes two HTML email templates:

1. **Account Verification—**Located at `src/main/resources/templates/email/account-verification.html`
2. **Password Reset—**Located at `src/main/resources/templates/email/password-reset.html`

Both templates are responsive and include:
- Personalized greeting with the user's name
- Clear display of the verification/reset code
- Instructions for using the code
- Security notices
- Copy-to-clipboard functionality (when supported by the email client)

## Configuration

The application can be configured through `src/main/resources/application.properties`:

```properties
# Server configuration
server.port=8081

# JavaMailSender Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Usage Examples

### Using cURL

#### Send Verification Email
```bash
curl -X POST http://localhost:8081/send-verification-email \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","code":"123456","username":"JohnDoe"}'
```

#### Send Password Reset Email
```bash
curl -X POST http://localhost:8081/send-forgot-password-email \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","code":"123456","username":"JohnDoe"}'
```

### Using Kotlin/Java Client

```kotlin
// Example using Spring RestTemplate
val restTemplate = RestTemplate()
val request = EmailCodeRequest(
    email = "user@example.com",
    code = "123456",
    username = "JohnDoe"
)

// Send verification email
val verificationResponse = restTemplate.postForEntity(
    "http://localhost:8081/send-verification-email",
    request,
    String::class.java
)

// Send password reset email
val resetResponse = restTemplate.postForEntity(
    "http://localhost:8081/send-forgot-password-email",
    request,
    String::class.java
)
```

## License

[MIT License](LICENSE)
