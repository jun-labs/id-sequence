package project.liquibase.app.core.user.application

import project.liquibase.app.core.user.domain.User

interface UserWriteUseCase {
    fun saveUserWithJpa(user: User): Long

    fun saveUserWithJdbc(user: User): Long
}
