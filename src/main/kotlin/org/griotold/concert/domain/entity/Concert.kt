package org.griotold.concert.domain.entity

import jakarta.persistence.*

@Entity
class Concert (
    name: String,
    singer: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "concert_id")
    var id: Long? = null

    var name: String = name
        protected set

    var singer: String = singer
        protected set
}