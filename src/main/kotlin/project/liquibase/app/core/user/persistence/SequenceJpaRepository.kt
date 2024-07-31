package project.liquibase.app.core.user.persistence

import org.springframework.data.jpa.repository.JpaRepository
import project.liquibase.app.core.user.domain.Sequence

interface SequenceJpaRepository : JpaRepository<Sequence, Long>
