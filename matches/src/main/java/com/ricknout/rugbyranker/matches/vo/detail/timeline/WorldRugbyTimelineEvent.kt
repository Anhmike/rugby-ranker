package com.ricknout.rugbyranker.matches.vo.detail.timeline

import java.util.UUID

data class WorldRugbyMatchTimelineEvent(
    val id: String,
    val phase: TimelineEventPhase,
    val timeSecs: Long,
    val timeLabel: String,
    val type: TimelineEventType,
    val typeName: String,
    val typeGroup: String,
    val points: Int,
    val playerId: Long,
    val teamId: Long
) {

    companion object {
        fun generateId() = UUID.randomUUID().toString()
    }
}
