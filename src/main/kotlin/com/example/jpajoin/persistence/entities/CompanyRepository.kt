package com.example.jpajoin.persistence.entities

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<CompanyProfile, Long> {
    //see https://roytuts.com/spring-boot-data-jpa-left-right-inner-and-cross-join-examples/ maybe it makes sense to create an own dto for reading here?
}