package project.liquibase.app.test.user.intergrationtest

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import project.liquibase.app.core.user.domain.User
import project.liquibase.app.core.user.facade.UserFacade
import project.liquibase.app.logger
import project.liquibase.app.test.DatabaseInitialization
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors.newFixedThreadPool
import kotlin.system.measureTimeMillis

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("[IntegrationTest] 데이터베이스 변경내역 조회 통합 테스트")
class SequenceGenerateIntegrationTest {

    companion object {
        private const val ONE_SECONDS = 1_000.0
        private const val TOTAL_COUNT = 3_000
    }

    private val log = logger()

    @Autowired
    lateinit var dbInitialization: DatabaseInitialization

    @AfterEach
    fun setUp() {
        dbInitialization.truncateAllEntity()
    }

    @Autowired
    private lateinit var userFacade: UserFacade

    @Test
    @DisplayName("Jpa로 사용자 Sequence 데이터를 저장했을 때, 저장한 개수와 결과가 동일한다.")
    fun whenSaveUserSequencesWithJpaThenResultShouldBeMatchedInsertCount() {
        val ids = mutableListOf<Long>()
        val latch = CountDownLatch(TOTAL_COUNT)
        val executor = newFixedThreadPool(256)

        val duration = measureTimeMillis {
            for (vUser in 1..TOTAL_COUNT) {
                executor.submit {
                    try {
                        val id = userFacade.saveSequenceWithJpa()
                        synchronized(ids) {
                            id?.let { ids.add(it) }
                        }
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
        executor.shutdown()
        assertEquals(TOTAL_COUNT, ids.size)

        log.info("Execution time: ${duration / ONE_SECONDS} seconds")
        log.info("Saved count: time: ${ids.size}")
    }

    @Test
    @DisplayName("JDBC 템플릿으로 사용자 Sequence 데이터를 저장했을 때, 저장한 개수와 결과가 동일한다.")
    fun whenSaveUserSequencesWithJdbcThenResultShouldBeMatchedInsertCount() {
        val ids = mutableListOf<Long>()
        val latch = CountDownLatch(TOTAL_COUNT)
        val executor = newFixedThreadPool(256)

        val duration = measureTimeMillis {
            for (vUser in 1..TOTAL_COUNT) {
                executor.submit {
                    try {
                        val id = userFacade.saveSequenceWithJdbc()
                        synchronized(ids) {
                            id?.let { ids.add(it) }
                        }
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
        executor.shutdown()
        assertEquals(TOTAL_COUNT, ids.size)

        log.info("Execution time: ${duration / ONE_SECONDS} seconds")
        log.info("Saved count: time: ${ids.size}")
    }

    @Test
    @DisplayName("Jpa로 사용자 Sequence를 저장하면 결과가 Null이 아니다.")
    fun whenSaveUserSequenceWithJpaThenResultShouldBeNotNull() = runBlocking {
        val sequenceId = userFacade.saveSequenceWithJpa()
        assertNotNull(sequenceId)
    }

    @Test
    @DisplayName("JDBC 템플릿으로 사용자 Sequence를 저장하면 결과가 Null이 아니다.")
    fun whenSaveUserSequenceWithJdbcThenResultShouldBeNotNull() = runBlocking {
        val sequenceId = userFacade.saveSequenceWithJdbc()
        assertNotNull(sequenceId)
    }

    @Test
    @DisplayName("Jpa로 사용자 데이터를 저장했을 때, 저장한 개수와 결과가 동일한다.")
    fun whenSaveUserWithJpaRepositoryThenResultShouldBeMatchedInsertCount() = runBlocking {
        val ids = mutableListOf<Long>()
        val latch = CountDownLatch(TOTAL_COUNT)
        val executor = newFixedThreadPool(256)

        val duration = measureTimeMillis {
            for (vUser in 1..TOTAL_COUNT) {
                executor.submit {
                    try {
                        val id = userFacade.saveUserWithJpa(createRandomUser())
                        synchronized(ids) {
                            id.let { ids.add(it) }
                        }
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
        executor.shutdown()
        assertEquals(TOTAL_COUNT, ids.size)

        log.info("Execution time: ${duration / ONE_SECONDS} seconds")
        log.info("Saved count: time: ${ids.size}")
    }

    @Test
    @DisplayName("JDBC 템플릿으로 사용자 데이터를 저장했을 때, 저장한 개수와 결과가 동일한다.")
    fun whenSaveUserWithJdbcRepositoryThenResultShouldBeMatchedInsertCount() = runBlocking {
        val ids = mutableListOf<Long>()
        val latch = CountDownLatch(TOTAL_COUNT)
        val executor = newFixedThreadPool(256)

        val duration = measureTimeMillis {
            for (vUser in 1..TOTAL_COUNT) {
                executor.submit {
                    try {
                        val id = userFacade.saveUserWithJdbc(createRandomUser())
                        synchronized(ids) {
                            id.let { ids.add(it) }
                        }
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
        executor.shutdown()
        assertEquals(TOTAL_COUNT, ids.size)

        log.info("Execution time: ${duration / ONE_SECONDS} seconds")
        log.info("Saved count: time: ${ids.size}")
    }

    fun createRandomUser(): User {
        return User(
            username = "user_${UUID.randomUUID()}",
            email = "${UUID.randomUUID()}@example.com",
            password = UUID.randomUUID().toString(),
            firstName = "John",
            lastName = "Doe",
            enabled = true,
            lastLogin = LocalDateTime.now().minusDays((1..365).random().toLong()),
            createdAt = LocalDateTime.now().minusDays((1..365).random().toLong()),
            updatedAt = LocalDateTime.now(),
            marketingConsent = listOf(true, false).random(),
            profileImageUrl = "https://example.com/images/${UUID.randomUUID()}.png",
            statusMessage = "This is a status message",
            phoneNumber = "123-456-7890",
            dateOfBirth = LocalDateTime.of(1990 + (0..30).random(), (1..12).random(), (1..28).random(), 0, 0),
            address = "1234 Main St, Anytown, USA"
        )
    }
}
