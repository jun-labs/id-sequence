package project.liquibase.app.core.user.persistence

import org.springframework.data.jpa.repository.JpaRepository
import project.liquibase.app.core.user.domain.User

interface UserJpaRepository : JpaRepository<User, Long>
