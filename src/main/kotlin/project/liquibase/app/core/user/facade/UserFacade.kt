package project.liquibase.app.core.user.facade

import org.springframework.stereotype.Component
import project.liquibase.app.core.user.application.SequenceWriteUseCase
import project.liquibase.app.core.user.application.UserWriteUseCase
import project.liquibase.app.core.user.domain.User

@Component
class UserFacade(
    private val sequenceWriteUseCase: SequenceWriteUseCase,
    private val userWriteUseCase: UserWriteUseCase,
) {

    fun saveSequenceWithJpa(): Long? {
        val seq = sequenceWriteUseCase.saveSequenceWithJpa()
        return seq.id
    }

    fun saveSequenceWithJdbc(): Long? {
        return sequenceWriteUseCase.saveSequenceWithJdbc()
    }

    fun saveUserWithJpa(user: User): Long {
        return userWriteUseCase.saveUserWithJpa(user)
    }

    fun saveUserWithJdbc(user: User): Long {
        return userWriteUseCase.saveUserWithJdbc(user)
    }
}
