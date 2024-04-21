package com.example.testoo.Domain.Repository

import com.example.testoo.Domain.models.Compte

interface UserRepository {
    suspend fun getCompteForUserId(userId: String): Compte?
}
