package com.example.nyarticles.domain.models

/**
 * Response sealed class
 *
 * Response (Loading, Success, Error)
 * */

sealed class Response {
    object Loading: Response()
    object Success: Response()
    object Error: Response()
}
