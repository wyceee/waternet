package wn.backend
//
//import org.springframework.boot.CommandLineRunner
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import wn.backend.models.Measure
//import wn.backend.models.User
//import wn.backend.models.Measure.Status
//import wn.backend.models.User.Role
//import wn.backend.repositories.MeasureRepository
//import wn.backend.repositories.UserRepository
//import java.time.Instant
//
//@Configuration
//class CreateInitialData {
//
//    @Bean
//    fun initData(
//        userRepository: UserRepository,
//        measureRepository: MeasureRepository
//    ): CommandLineRunner {
//        return CommandLineRunner {
//            // Create dummy users
//            val user1 = User(
//                name = "John Doe",
//                email = "john.doe@example.com",
//                hashedPassword = "hashedpassword123",
//                role = Role.USER,
//                wallet = "0x1234567890abcdef1234567890abcdef12345678"
//            )
//
//            val user2 = User(
//                name = "Jane Smith",
//                email = "jane.smith@example.com",
//                hashedPassword = "hashedpassword456",
//                role = Role.SUPERVISOR,
//                wallet = "0xabcdef1234567890abcdef1234567890abcdef12"
//            )
//
//            userRepository.saveAll(listOf(user1, user2))
//
//            // Create dummy measures
//            val measure1 = Measure(
//                user = user1,
//                description = "Green Roof Installation",
//                photoUrl = "http://example.com/photo1.jpg",
//                location = "New York",
//                timestamp = Instant.now(),
//                status = Status.APPROVED,
//                measureType = "Green Roof",
//                area = 120.5,
//                capacity = 300.0
//            )
//
//            val measure2 = Measure(
//                user = user1,
//                description = "Rain Garden Project",
//                photoUrl = "http://example.com/photo2.jpg",
//                location = "Los Angeles",
//                timestamp = Instant.now(),
//                status = Status.PENDING,
//                measureType = "Rain Garden",
//                area = 80.0,
//                capacity = 200.0
//            )
//
//            val measure3 = Measure(
//                user = user2,
//                description = "Permeable Pavement",
//                photoUrl = "http://example.com/photo3.jpg",
//                location = "Chicago",
//                timestamp = Instant.now(),
//                status = Status.REJECTED,
//                measureType = "Pavement",
//                area = 150.0,
//                capacity = 400.0
//            )
//
//            measureRepository.saveAll(listOf(measure1, measure2, measure3))
//        }
//    }
//}