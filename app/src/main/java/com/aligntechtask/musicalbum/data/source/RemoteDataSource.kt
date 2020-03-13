package com.aligntechtask.musicalbum.data.source

import android.content.Context
import com.aligntechtask.musicalbum.data.FakeMovieData

class RemoteDataSource(context: Context) :MoviesDataSource{

    private var Category_DATA = ArrayList<Category>()
    private var Album_DATA = FakeMovieData.albums

    init {
        /*for (movie in FakeMovieData.movies){
            addMovie(movie)
        }*/
        for (category in CategoryData.movies){
            addCategory(category)
        }
    }



    override fun getCategories(): List<Category> {

        return ArrayList(Category_DATA)
    }

    override fun getMovie(): com.aligntechtask.musicalbum.data.Album {
        TODO("Not yet implemented")
    }


    private fun addCategory(
        category: Category
    ){

        Category_DATA.add(category)
    }
    public fun getCategory( pos: Int):Category{

       return Category_DATA.get(pos)
    }
    public fun getAlbum( pos: Int): com.aligntechtask.musicalbum.data.Album {

       return Album_DATA.get(pos)
    }

}














