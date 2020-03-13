package com.aligntechtask.musicalbum.factory

import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.request.RequestOptions
import com.aligntechtask.musicalbum.data.source.MoviesDataSource
import com.aligntechtask.musicalbum.data.source.RemoteDataSource
import com.aligntechtask.musicalbum.ui.album.*

class MovieFragmentFactory(
    private val requestOptions: RequestOptions? = null,
    private val dataSource: RemoteDataSource? = null,
    private val moviesDataSource1: MoviesDataSource? = null
) : FragmentFactory(){

    private val TAG: String = "AppDebug"

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            AlbumListFragment::class.java.name -> {
                if (dataSource != null) {
                    AlbumListFragment(dataSource)
                } else {
                    super.instantiate(classLoader, className)
                }
            } AlbumCategoryFragment::class.java.name -> {
                if (dataSource != null) {
                    AlbumCategoryFragment(dataSource)
                } else {
                    super.instantiate(classLoader, className)
                }
            }

            AlbumDetailFragment::class.java.name -> {
                if(requestOptions != null
                    && dataSource != null){
                    AlbumDetailFragment(
                        requestOptions,
                        dataSource
                    )
                }
                else{
                    super.instantiate(classLoader, className)
                }
            }



            else -> {
                super.instantiate(classLoader, className)
            }
        }

}













