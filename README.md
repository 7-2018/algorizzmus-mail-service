# Algorizzmus Mail Service

A microservice for sending transactional emails for the Algorizzmus authentication system. This service handles sending account verification and password reset emails.

## How to run the application

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

## Environment setup and dependencies

### Prerequisites

- JDK 17 or higher
- Gradle
- SMTP server access (Gmail account configured for this example)

### Technologies

- Kotlin
- Spring Boot
- Spring Mail (JavaMailSender)
- Thymeleaf (for email templates)

### Features

- Send account verification emails with verification codes
- Send password reset emails with reset codes
- HTML email templates with responsive design
- Fallback to plain text emails if template processing fails

## High-level architecture overview

The Algorizzmus Mail Service follows a standard Spring Boot microservice architecture:

1. **Controller Layer** - Receives email requests
   - `EmailController` - Handles requests and delegates to the service layer

2. **Service Layer** - Business logic for email processing
   - `EmailService` - Processes email requests, renders templates, and sends emails

3. **Template Engine** - Renders HTML email templates
   - Thymeleaf - Processes HTML templates with dynamic content

4. **Mail Sender** - Handles SMTP communication
   - JavaMailSender - Sends both HTML and plain text emails

The service is designed with a fallback mechanism that defaults to plain text emails if HTML template processing fails, ensuring email delivery even in case of template errors.

```
[Client] → [EmailController] → [EmailService] → [JavaMailSender] → [SMTP Server]
                                     ↓
                              [Thymeleaf Engine]
                                     ↓
                            [HTML Email Templates]
```

## Configuration instructions

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

### Email Templates

The service includes two HTML email templates:

1. **Account Verification—**Located at `src/main/resources/templates/email/account-verification.html`
2. **Password Reset—**Located at `src/main/resources/templates/email/password-reset.html`

Both templates are responsive and include:
- Personalized greeting with the user's name
- Clear display of the verification/reset code
- Instructions for using the code
- Security notices
- Copy-to-clipboard functionality (when supported by the email client)

## License

[MIT License](LICENSE)
