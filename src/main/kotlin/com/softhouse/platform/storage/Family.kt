package com.softhouse.platform.storage

import javax.persistence.*

@Entity
@Table(name = "Family")
data class Family(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "User_Id", updatable = false, nullable = false)
    val userId: String,

    @Column(name = "Family_Name", updatable = false, nullable = false)
    val name: String,

    @OneToMany(mappedBy = "family", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    private val familyMembers: MutableList<Person> = mutableListOf()
) {
    constructor() : this(null, "", "", mutableListOf())

    fun getFamilyMembers(): MutableList<Person> {
        return familyMembers
    }

    fun addFamilyMember(newFamilyMember: Person) {
        familyMembers += newFamilyMember
    }

    fun removeFamilyMember(familyMemberToDelete: Person) {
        familyMembers -= familyMemberToDelete
    }
}

