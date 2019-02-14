package com.andrescoulson.mymatchs.model

data class Match(
    val awayTeam: AwayTeam,
    val competitionStage: CompetitionStage,
    val date: String?,
    val homeTeam: HomeTeam,
    val id: Int,
    val score: Score?,
    val state: String,
    val type: String,
    val venue: Venue
)

data class HomeTeam(
    val abbr: String,
    val alias: String,
    val id: Int,
    val name: String,
    val shortName: String
)

data class Venue(
    val id: Int,
    val name: String
)

data class AwayTeam(
    val abbr: String,
    val alias: String,
    val id: Int,
    val name: String,
    val shortName: String
)

data class CompetitionStage(
    val competition: Competition
)

data class Competition(
    val id: Int,
    val name: String
)

data class Score(
    val away: Int,
    val home: Int,
    val winner: String
)