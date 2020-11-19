package com.example.jpajoin

import com.example.jpajoin.persistence.entities.CompanyProfile
import com.example.jpajoin.persistence.entities.CompanyRepository
import com.example.jpajoin.persistence.entities.Role
import com.example.jpajoin.persistence.entities.User
import com.example.jpajoin.persistence.entities.UserCompanyRoles
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@SpringBootApplication
class JpaJoinApplication(val companyRepository: CompanyRepository) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments) {
        val user = User(firstName = "Hans")
        val companyProfile = CompanyProfile(profileId = "1234567CDEF", companyId = "1234567CDEF", companyName = "My company ${Random.nextInt()}")
        companyProfile.users
        val userCompanyRoles = UserCompanyRoles(user = user, company = companyProfile, roles = setOf(Role.ADMIN, Role.MANAGER, Role.USER))
        companyProfile.users = listOf(userCompanyRoles)
        user.companies = listOf(userCompanyRoles)
        companyRepository.save(companyProfile)

        println("${companyRepository.count()}")

        val findAll = companyRepository.findAll()

        //causes a lot of subselects to get all the users and roles per company - a single resultset would be much better
        findAll.flatMap { it.users }.forEach { println("${it.user.firstName}, ${it.company.companyName}, ${it.roles}") }
        //maybe it would be even better to make the path not "traversible in the entity"
    }
}

fun main(args: Array<String>) {
    runApplication<JpaJoinApplication>(*args)
}
