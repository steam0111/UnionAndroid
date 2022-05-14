package com.itrocket.union.authSelectUser.domain.dependencies

interface AuthSelectUserRepository {
    suspend fun getExistUsers(searchText: String): List<String>
}