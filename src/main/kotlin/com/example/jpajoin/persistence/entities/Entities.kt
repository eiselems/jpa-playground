package com.example.jpajoin.persistence.entities

import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "company_profile")
data class CompanyProfile(

        @Id
        @Column(name = "oid")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var oid: Long = 0,

        @Column(name = "profile_id")
        val profileId: String,

        @Column(name = "company_id")
        val companyId: String,

        @Column(name = "company_name")
        val companyName: String,

        @OneToMany(
                mappedBy = "company",
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY
        )
        var users: List<UserCompanyRoles> = ArrayList(),
)

@Entity
@Table(name = "user_profile")
data class User(
        @Id
        @Column(name = "oid")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var oid: Long = 0,

        val firstName: String,

        @OneToMany(
                mappedBy = "user",
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY
        )
        var companies: List<UserCompanyRoles> = ArrayList(),
)

@Entity
@Table(name = "user_company_roles")
data class UserCompanyRoles(
        @Id
        @Column(name = "oid")
        //TODO: add sequence
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var oid: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])

        var user: User,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var company: CompanyProfile,


        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "roles", joinColumns = [JoinColumn(name = "oid")])
        @Enumerated(EnumType.STRING)
        val roles: Set<Role> = HashSet(),
)

enum class Role {
        ADMIN, MANAGER, USER
}
