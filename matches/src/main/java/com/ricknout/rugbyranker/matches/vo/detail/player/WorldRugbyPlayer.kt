package com.ricknout.rugbyranker.matches.vo.detail.player

data class WorldRugbyPlayer(
    val id: Long,
    val name: String,
    val firstName: String,
    val lastName: String,
    val initials: String,
    val position: PlayerPosition,
    val positionName: String,
    val positionNumber: Int
)
