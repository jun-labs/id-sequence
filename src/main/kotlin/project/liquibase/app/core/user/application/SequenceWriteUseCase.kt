package project.liquibase.app.core.user.application

import project.liquibase.app.core.user.domain.Sequence

interface SequenceWriteUseCase {
    fun saveSequenceWithJpa(): Sequence

    fun saveSequenceWithJdbc(): Long
}
