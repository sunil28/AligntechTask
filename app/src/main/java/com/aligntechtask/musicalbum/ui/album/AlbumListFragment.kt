package com.aligntechtask.musicalbum.ui.album

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aligntechtask.musicalbum.R
import com.aligntechtask.musicalbum.data.FakeMovieData.FAKE_NETWORK_DELAY
import com.aligntechtask.musicalbum.data.Album
import com.aligntechtask.musicalbum.data.Settings
import com.aligntechtask.musicalbum.data.source.RemoteDataSource
import com.aligntechtask.musicalbum.ui.UICommunicationListener
import com.aligntechtask.musicalbum.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONException
import org.json.JSONObject
import java.lang.ClassCastException

class AlbumListFragment(
    val dataSource: RemoteDataSource
) : Fragment(),
    AlbumsListAdapter.Interaction
{
    private val TAG: String = "AppDebug"
    private var Url_val: String = ""
    private var MOVIES_REMOTE_DATA = ArrayList<Album>()
    override fun onItemSelected(item: Album) {
        activity?.run {
            val bundle = Bundle()

            bundle.putParcelable("album", item)
            supportFragmentManager.beginTransaction()
                .add(R.id.container, AlbumDetailFragment::class.java, bundle)
                .addToBackStack("AlbumListFragment")
                .commit()
            /*val popupMenu: PopupMenu = PopupMenu(this,recycler_view)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_share ->
                        Toast.makeText(requireContext(), "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.action_addtophotos ->
                        Toast.makeText(requireContext(), "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.action_copy ->
                        Toast.makeText(requireContext(), "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popupMenu.show()*/
        }
    }

    lateinit var listAdapter: AlbumsListAdapter
    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("url ",arguments?.getString("Url_val"))
        Url_val = arguments?.getString("Url_val").toString()
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        val settings = Settings(requireContext())
        Url_val =  "https://rss.itunes.apple.com/api/v1/"+settings.countryCodeValue+Url_val
        Log.e("url ",Url_val)
        getData(Url_val)

    }

    private fun getData(url:String){

            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { s ->
                    try {
                        Log.i("response",s)
                        val obj = JSONObject(s)

                        val feed = obj.getJSONObject("feed")
                        val array = feed.getJSONArray("results")

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val genres = objectArtist.getJSONArray("genres")
                            val genres_list  = ArrayList<String>()
                            Log.i("response",objectArtist.toString())
                            var artistName= ""

                            val mve = Album(
                                get_json_val( objectArtist,"id"),
                                get_json_val( objectArtist,"name"),
                                get_json_val( objectArtist,"artworkUrl100"),
                                get_json_val( objectArtist,"copyright")

                            )
                            addMovie(mve)

                        }
initRecyclerView();
                        EspressoIdlingResource.increment()
                        uiCommunicationListener.loading(true)
                        val job = GlobalScope.launch(IO) {
                            delay(FAKE_NETWORK_DELAY)
                        }
                        job.invokeOnCompletion{
                            GlobalScope.launch(Main){
                                EspressoIdlingResource.decrement()
                                uiCommunicationListener.loading(false)
                                listAdapter.submitList(MOVIES_REMOTE_DATA)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(requireContext(), volleyError.message, Toast.LENGTH_LONG).show() })

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add<String>(stringRequest)


    }
    private fun addMovie(
        id: String,
        title: String,
        image: String,
        description: String
    ){
        val movie = Album(
            id = id,
            title = title,
            image = image,
            description = description

        )

        MOVIES_REMOTE_DATA.add(movie)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = GridLayoutManager(activity,2)
            addItemDecoration(GridItemDecoration(10, 2))
            listAdapter = AlbumsListAdapter(this@AlbumListFragment)

            adapter = listAdapter
        }

    }
    private fun addMovie(
        movie: Album
    ){
        Log.i("id",""+ movie.id)
        Log.i("title",movie. title)
        Log.i("image", movie.image)
        Log.i("description",movie. description)
        MOVIES_REMOTE_DATA.add(movie)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch (e: ClassCastException){
            Log.e(TAG, "Must implement interface in $activity: ${e.message}")
        }
    }
    fun get_json_val(json_obj: JSONObject,key_val: String):String{
        var json_val = ""
        if(json_obj.has(key_val) &&  json_obj.getString(key_val) !=null)
            json_val = json_obj.getString(key_val)

        return json_val
    }
}




















