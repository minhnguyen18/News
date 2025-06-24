package com.example.news.model.model

data class NYTResponse(
    val results: List<NYTResult>
)

data class NYTResult(
    val title: String,
    val byline: String,
    val published_date: String,
    val multimedia: List<NYTMedia>?,
    val abstract: String?,
    val url: String?,
)

data class NYTMedia(
    val url: String
)
