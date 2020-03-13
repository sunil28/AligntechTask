package com.aligntechtask.musicalbum.data.source

interface MoviesDataSource {

    fun getCategories(): List<Category>
    fun getMovie(): com.aligntechtask.musicalbum.data.Album
}