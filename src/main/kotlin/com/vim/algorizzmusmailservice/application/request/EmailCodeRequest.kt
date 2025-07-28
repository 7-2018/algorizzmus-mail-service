package com.vim.algorizzmusmailservice.application.request

data class EmailCodeRequest(
    val email: String,
    val code: String,
    var username: String,
)
