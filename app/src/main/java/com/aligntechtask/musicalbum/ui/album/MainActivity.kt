package com.aligntechtask.musicalbum.ui.album

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.request.RequestOptions
import com.aligntechtask.musicalbum.R
import com.aligntechtask.musicalbum.data.source.MoviesDataSource
import com.aligntechtask.musicalbum.data.source.RemoteDataSource
import com.aligntechtask.musicalbum.databinding.ActivityMainBinding
import com.aligntechtask.musicalbum.factory.MovieFragmentFactory
import com.aligntechtask.musicalbum.ui.UICommunicationListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    UICommunicationListener
{

    override fun loading(isLoading: Boolean) {
        if (isLoading)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.INVISIBLE


    }

    // dependencies (typically would be injected with dagger)
    lateinit var requestOptions: RequestOptions
    lateinit var dataSource: RemoteDataSource
    lateinit var moviesDataSource1: MoviesDataSource
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        supportFragmentManager.fragmentFactory = MovieFragmentFactory(
            requestOptions,
            dataSource
            )
        super.onCreate(savedInstanceState)


        //toolbar.setLogo(R.drawable.logo) // setting a logo in toolbar
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        binding.setLifecycleOwner(this)
        init()

    }
    val RECORD_REQUEST_CODE:Int = 101
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)
    }
    private fun init(){
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("permission", "Permission to record denied")
            makeRequest()
        }
        if(supportFragmentManager.fragments.size == 0){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, AlbumCategoryFragment::class.java, null)

                .commit()
        }
    }

    private fun initDependencies(){
        if(!::requestOptions.isInitialized){
            // glide
            requestOptions = RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.default_image)
        }
        if(!::dataSource.isInitialized){
            // Data Source
            dataSource = RemoteDataSource(this)
        }
    }



}







