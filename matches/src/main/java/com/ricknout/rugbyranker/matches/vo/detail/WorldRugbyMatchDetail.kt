package com.ricknout.rugbyranker.matches.vo.detail

import com.ricknout.rugbyranker.matches.vo.detail.official.WorldRugbyOfficial
import com.ricknout.rugbyranker.matches.vo.detail.player.WorldRugbyPlayer
import com.ricknout.rugbyranker.matches.vo.detail.timeline.WorldRugbyMatchTimelineEvent

data class WorldRugbyMatchDetail(
    val firstTeamCaptainId: Long,
    val firstTeamPlayers: List<WorldRugbyPlayer>,
    val secondTeamCaptainId: Long,
    val secondTeamPlayers: List<WorldRugbyPlayer>,
    val officials: List<WorldRugbyOfficial>,
    val timelineEvents: List<WorldRugbyMatchTimelineEvent>
)
