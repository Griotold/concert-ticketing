package org.griotold.concert.domain.entity

import jakarta.persistence.*

@Entity
class Member (
    name: String,
    point: Int,
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    var id: Long? = null

    var name: String = name
        protected set

    var point: Int = point
        protected set

}