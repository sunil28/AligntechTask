package com.aligntechtask.musicalbum.data.source


object CategoryData {

    const val FAKE_NETWORK_DELAY = 1000L

    val movies = arrayOf(
        Category(
            0,
            "Coming Soon",
            "/apple-music/coming-soon/all/25/explicit.json"

        ),
        Category(
            1,
            "Top Rated",
            "/apple-music/hot-tracks/all/25/explicit.json"
        ),
        Category(
            2,
            "Hot Tracks",
            "/apple-music/new-releases/all/25/explicit.json"
        ),
        Category(
            3,
            "New Releases",
            "/apple-music/top-albums/all/25/explicit.json"
        )
    )
}