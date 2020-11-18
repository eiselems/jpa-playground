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

@SpringBootApplication
class JpaJoinApplication(val companyRepository: CompanyRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val user = User(firstName = "Hans")
        val companyProfile = CompanyProfile(profileId = "1234567CDEF", companyId = "1234567CDEF", companyName = "My company")
        val userCompanyRoles = UserCompanyRoles(user = user, company = companyProfile, roles = setOf(Role.ADMIN, Role.MANAGER, Role.USER))
        companyProfile.users = listOf(userCompanyRoles)
        user.companies = listOf(userCompanyRoles)
        companyRepository.save(companyProfile)


        println("${companyRepository.count()}")

        companyProfile.users.forEach { print("${it.user.firstName} ${it.company.companyName} ${it.roles}")}
    }
}

fun main(args: Array<String>) {
    runApplication<JpaJoinApplication>(*args)
}
