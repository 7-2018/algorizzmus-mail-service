package com.vim.algorizzmusmailservice.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {
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

    fun sendVerificationEmail(
        to: String,
        code: String,
    ) {
        val body = "Your verification code is $code"
        sendEmail(to, REGISTRATION_EMAIL_SUBJECT, body)
    }

    fun sendPasswordResetEmail(
        to: String,
        code: String,
    ) {
        val body = "Your password reset code is $code"
        sendEmail(to, PASSWORD_RESET_EMAIL_SUBJECT, body)
    }

    companion object {
        private const val REGISTRATION_EMAIL_SUBJECT = "Verify your email"
        private const val PASSWORD_RESET_EMAIL_SUBJECT = "Reset your password"
    }
}
