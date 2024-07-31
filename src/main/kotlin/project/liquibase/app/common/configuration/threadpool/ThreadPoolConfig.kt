package project.liquibase.app.common.configuration.threadpool

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class ThreadPoolConfig {

    @Bean(name = ["taskExecutor"])
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor().apply {
            corePoolSize = 200
            maxPoolSize = 400
            queueCapacity = 500
            initialize()
        }
        executor.setThreadNamePrefix("Async-")
        return executor
    }
}
