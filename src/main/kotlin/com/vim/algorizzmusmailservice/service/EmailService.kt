package com.vim.algorizzmusmailservice.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailService(
    private val mailSender: JavaMailSender, private val templateEngine: TemplateEngine
) {
    @Value("\${spring.mail.username}")
    private lateinit var mailUsername: String

    private fun sendEmail(
        to: String,
        subject: String,
        text: String,
    ) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = subject
        message.text = text
        mailSender.send(message)
    }

    private fun sendHtmlEMail(
        to: String,
        subject: String,
        htmlContent: String,
    ) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom(mailUsername)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(htmlContent, true)
        mailSender.send(message)
    }

    private fun processTemplate(
        templateName: String,
        variables: Map<String, Any>,
    ): String? {
        val context = Context()
        variables.forEach { (key, value) -> context.setVariable(key, value) }
        return templateEngine.process(templateName, context)

    }

    fun sendVerificationEmail(
        to: String,
        code: String,
        username: String,
    ) {
        val htmlContent = processTemplate(
            "email/account-verification",
            mapOf(
                "username" to username,
                "code" to code,
            ),
        )

        if (htmlContent != null) {
            sendHtmlEMail(to, REGISTRATION_EMAIL_SUBJECT, htmlContent)
        } else {
            val body = "Your verification code is $code"
            sendEmail(to, REGISTRATION_EMAIL_SUBJECT, body)
        }
    }

    fun sendForgotPasswordEmail(
        to: String,
        code: String,
        username: String,
    ) {

        val htmlContent = processTemplate(
            "email/password-reset",
            mapOf(
                "username" to username,
                "code" to code,
            ),
        )

        if (htmlContent != null) {
            sendHtmlEMail(to, PASSWORD_RESET_EMAIL_SUBJECT, htmlContent)
        } else {
            val body = "Your code for password reset is $code"
            sendEmail(to, PASSWORD_RESET_EMAIL_SUBJECT, body)
        }
    }

    companion object {
        private const val REGISTRATION_EMAIL_SUBJECT = "Verify your email"
        private const val PASSWORD_RESET_EMAIL_SUBJECT = "Reset your password"
    }
}

