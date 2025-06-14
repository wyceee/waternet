package wn.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import wn.backend.models.Measure
import wn.backend.models.User
import wn.backend.models.Role
import wn.backend.models.Measure.Status
import wn.backend.repositories.MeasureRepository
import wn.backend.repositories.UserRepository
import java.time.Instant

@Configuration
class CreateInitialData {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Bean
    fun initData(
        userRepository: UserRepository,
        measureRepository: MeasureRepository
    ): CommandLineRunner {
        return CommandLineRunner {

            val user1 = User(
                name = "User",
                email = "user@user",
                hashedPassword = passwordEncoder.encode("user"),
                role = Role.USER,
                wallet = "0x5104B63372cA589a9AB34b14177237379AC1574A"
            )

            val user2 = User(
                name = "SupervisorGuy",
                email = "admin@admin",
                hashedPassword = passwordEncoder.encode("admin"),
                role = Role.SUPERVISOR,
                wallet = "0x663aaf2f930bf0f7fa1f4eda9de0a00187c1ca10"
            )

            userRepository.saveAll(listOf(user1, user2))

            val measure1 = Measure(
                user = user1,
                description = "Green Roof Installation",
                photoUrl = "/uploads/images/1749667448023.png",
                location = "New York",
                timestamp = Instant.now(),
                status = Status.PENDING,
                measureType = "Green Roof",
                area = 120.5,
                capacity = 300.0
            )

            val measure2 = Measure(
                user = user1,
                description = "Rain Garden Project",
                photoUrl = "/uploads/images/1749670837144.png",
                location = "Los Angeles",
                timestamp = Instant.now(),
                status = Status.PENDING,
                measureType = "Rain Garden",
                area = 80.0,
                capacity = 200.0
            )

            val measure3 = Measure(
                user = user1,
                description = "Permeable Pavement",
                photoUrl = "/uploads/images/1749672013997.png",
                location = "Chicago",
                timestamp = Instant.now(),
                status = Status.PENDING,
                measureType = "Pavement",
                area = 150.0,
                capacity = 400.0
            )

            measureRepository.saveAll(listOf(measure1, measure2, measure3))
        }
    }
}