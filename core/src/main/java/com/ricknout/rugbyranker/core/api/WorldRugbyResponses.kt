package com.ricknout.rugbyranker.core.api

data class Effective(
    val label: String,
    val millis: Long,
    val gmtOffset: Float
)

data class Team(
    val id: Long,
    val name: String,
    val abbreviation: String?
)

data class Entry(
    val pos: Int,
    val previousPos: Int,
    val pts: Float,
    val previousPts: Float,
    val matches: Int,
    val team: Team
)

data class WorldRugbyRankingsResponse(
    val effective: Effective,
    val entries: List<Entry>,
    val label: String
)

data class PageInfo(
    val page: Int,
    val numPages: Int,
    val pageSize: Int,
    val numEntries: Int
)

data class Venue(
    val id: Long,
    val name: String,
    val city: String,
    val country: String
)

data class Clock(
    val secs: Int,
    val label: String
)

data class Event(
    val id: Long,
    val label: String,
    val sport: String,
    val start: Effective,
    val end: Effective,
    val rankingsWeight: Float
)

data class Match(
    val matchId: Long,
    val description: String?,
    val venue: Venue?,
    val time: Effective,
    val attendance: Int,
    val teams: List<Team>,
    val scores: List<Int>,
    val status: String,
    val clock: Clock?,
    val events: List<Event>
)

data class WorldRugbyMatchesResponse(
    val pageInfo: PageInfo,
    val content: List<Match>
)

data class TeamSummary(
    val id: Long,
    val country: String,
    val naming: Naming
)

data class Naming(
    val name: String,
    val abbr: String
)

data class WorldRugbyTeamsResponse(
    val teams: List<TeamSummary>
)

data class Article(
    val id: Long,
    val type: String,
    val title: String,
    val description: String?,
    val publishFrom: Long,
    val language: String,
    val canonicalUrl: String,
    val subtitle: String?,
    val summary: String?,
    val imageUrl: String?,
    val onDemandUrl: String?
)

data class WorldRugbyArticlesResponse(
    val pageInfo: PageInfo,
    val content: List<Article>
)

data class Time(
    val secs: Int,
    val label: String
)

data class TimelineEvent(
    val phase: String,
    val time: Time,
    val type: String,
    val typeLabel: String,
    val group: String,
    val points: Int,
    val playerId: Long,
    val teamIndex: Int
)

data class WorldRugbyMatchTimelineResponse(
    val match: Match,
    val timeline: List<TimelineEvent>
)

data class Name(
    val known: String?,
    val official: String
)

data class NameDetail(
    val initials: String,
    val first: Name,
    val last: Name,
    val display: String
)

data class Player(
    val id: Long,
    val name: NameDetail
)

data class PlayerDetail(
    val player: Player,
    val number: String,
    val position: String,
    val positionLabel: String
)

data class TeamList(
    val list: List<PlayerDetail>?,
    val captainId: Long
)

data class ScoringDetail(
    val phase: String,
    val time: Time,
    val type: String,
    val typeLabel: String,
    val playerId: Long?
)

data class Scoring(
    val Con: List<ScoringDetail>?,
    val Pen: List<ScoringDetail>?,
    val Try: List<ScoringDetail>?
)

data class TeamDetail(
    val teamList: TeamList,
    val scoring: Scoring
)

data class Official(
    val id: Long,
    val name: NameDetail
)

data class OfficialDetail(
    val official: Official,
    val position: String
)

data class WorldRugbyMatchSummaryResponse(
    val match: Match,
    val teams: List<TeamDetail>,
    val officials: List<OfficialDetail>
)
