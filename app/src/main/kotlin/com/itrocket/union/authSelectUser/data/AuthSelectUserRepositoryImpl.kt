package com.itrocket.union.authSelectUser.data

import com.itrocket.union.authSelectUser.domain.dependencies.AuthSelectUserRepository

class AuthSelectUserRepositoryImpl : AuthSelectUserRepository {
    override suspend fun getExistUsers(searchText: String): List<String> {
        return listOf("roman", "admin", "test").filter {
            it.contains(searchText)
        }
    }
}