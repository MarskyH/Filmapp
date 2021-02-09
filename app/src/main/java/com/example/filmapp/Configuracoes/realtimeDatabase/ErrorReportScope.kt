package com.example.filmapp.Configuracoes.realtimeDatabase

data class ErrorReportScope(
    var idUserReported: String,
    var errorLocation: String,
    var errorDescription: String,
    var errorComponent: String,
    var error: String
)