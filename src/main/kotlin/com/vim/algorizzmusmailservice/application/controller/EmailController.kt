package com.vim.algorizzmusmailservice.application.controller

import com.vim.algorizzmusmailservice.application.request.EmailCodeRequest
import com.vim.algorizzmusmailservice.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailController(private val emailService: EmailService) {
    @PostMapping("/send-verification-email")
    fun sendVerificationEmail(
        @RequestBody request: EmailCodeRequest,
    ): ResponseEntity<String> {
        emailService.sendVerificationEmail(request.email, request.code, request.username)
        return ResponseEntity.ok("Verification email sent successfully.")
    }

    @PostMapping("/send-forgot-password-email")
    fun sendForgotPasswordEmail(
        @RequestBody request: EmailCodeRequest,
    ): ResponseEntity<String> {
        emailService.sendForgotPasswordEmail(request.email, request.code, request.username)
        return ResponseEntity.ok("Password reset email sent successfully.")
    }
}
