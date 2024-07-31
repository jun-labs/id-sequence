package project.liquibase.app.core.user.application.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import project.liquibase.app.core.user.application.SequenceWriteUseCase
import project.liquibase.app.core.user.application.UserWriteUseCase
import project.liquibase.app.core.user.domain.Sequence
import project.liquibase.app.core.user.domain.User
import project.liquibase.app.core.user.persistence.SequenceJpaRepository
import project.liquibase.app.core.user.persistence.UserJpaRepository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Statement

@Service
class UserWriteService(
    private val sequenceJpaRepository: SequenceJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val jdbcTemplate: JdbcTemplate,
) : SequenceWriteUseCase, UserWriteUseCase {

    @Transactional
    override fun saveSequenceWithJpa(): Sequence {
        return sequenceJpaRepository.save(Sequence())
    }

    override fun saveSequenceWithJdbc(): Long {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val sql = "INSERT INTO user_sequence VALUES (NULL, FALSE)"
        try {
            jdbcTemplate.update({ connection: Connection ->
                val ps: PreparedStatement =
                    connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
                ps
            }, keyHolder)
            return keyHolder.key!!.toLong()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    @Transactional
    override fun saveUserWithJpa(user: User): Long {
        val newUser = userJpaRepository.save(user)
        return newUser.id!!
    }

    @Transactional
    override fun saveUserWithJdbc(user: User): Long {
        val sql = """
            INSERT INTO User (
                username, email, password, first_name, last_name, enabled, 
                last_login, created_at, updated_at, marketing_consent, profile_image_url, 
                status_message, phone_number, date_of_birth, address
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        try {
            jdbcTemplate.update({ connection: Connection ->
                val ps: PreparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, user.username)
                ps.setString(2, user.email)
                ps.setString(3, user.password)
                ps.setString(4, user.firstName)
                ps.setString(5, user.lastName)
                ps.setBoolean(6, user.enabled)
                ps.setObject(7, user.lastLogin)
                ps.setObject(8, user.createdAt)
                ps.setObject(9, user.updatedAt)
                ps.setBoolean(10, user.marketingConsent)
                ps.setString(11, user.profileImageUrl)
                ps.setString(12, user.statusMessage)
                ps.setString(13, user.phoneNumber)
                ps.setObject(14, user.dateOfBirth)
                ps.setString(15, user.address)
                ps
            }, keyHolder)
            return keyHolder.key?.toLong() ?: throw Exception("Failed to insert user")
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }
}
