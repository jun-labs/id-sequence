package project.liquibase.app.core.user.domain


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "User")
class User(
    @Column(name = "username", nullable = false, length = 50)
    var username: String? = null,

    @Column(name = "email", nullable = false, unique = true, length = 100)
    var email: String? = null,

    @Column(name = "password", nullable = false, length = 255)
    var password: String? = null,

    @Column(name = "first_name", length = 50)
    var firstName: String? = null,

    @Column(name = "last_name", length = 50)
    var lastName: String? = null,

    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = false,

    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "marketing_consent", nullable = false)
    var marketingConsent: Boolean = false,

    @Column(name = "profile_image_url", length = 255)
    var profileImageUrl: String? = null,

    @Column(name = "status_message", length = 255)
    var statusMessage: String? = null,

    @Column(name = "phone_number", length = 20)
    var phoneNumber: String? = null,

    @Column(name = "date_of_birth")
    var dateOfBirth: LocalDateTime? = null,

    @Column(name = "address", length = 255)
    var address: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

