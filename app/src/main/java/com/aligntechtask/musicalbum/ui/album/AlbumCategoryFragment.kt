package com.aligntechtask.musicalbum.ui.album

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aligntechtask.musicalbum.R
import com.aligntechtask.musicalbum.data.FakeMovieData.FAKE_NETWORK_DELAY
import com.aligntechtask.musicalbum.data.source.Category
import com.aligntechtask.musicalbum.data.source.MoviesDataSource
import com.aligntechtask.musicalbum.ui.UICommunicationListener
import com.aligntechtask.musicalbum.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.ClassCastException

class AlbumCategoryFragment(
    val moviesDataSource: MoviesDataSource
) : Fragment(),
    CategoriesListAdapter.Interaction
{
    private val TAG: String = "AppDebug"

    override fun onItemSelected( item: Category) {
        activity?.run {

            val bundle = Bundle()
            bundle.putString("Url_val",item.Url)

            supportFragmentManager.beginTransaction()
                    .add(R.id.container, AlbumListFragment::class.java, bundle)
                .addToBackStack("AlbumCategoryFragment")
                    .commit()

        }
    }

    lateinit var listAdapter: CategoriesListAdapter
    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        getData()
    }

    private fun getData(){
        EspressoIdlingResource.increment()
        uiCommunicationListener.loading(true)
        val job = GlobalScope.launch(IO) {
            delay(FAKE_NETWORK_DELAY)
        }
        job.invokeOnCompletion{
            GlobalScope.launch(Main){
                EspressoIdlingResource.decrement()
                uiCommunicationListener.loading(false)
                listAdapter.submitList(moviesDataSource.getCategories())
            }
        }
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = GridLayoutManager(activity,2)
            addItemDecoration(GridItemDecoration(10, 2))
            listAdapter = CategoriesListAdapter(this@AlbumCategoryFragment)
            adapter = listAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch (e: ClassCastException){
            Log.e(TAG, "Must implement interface in $activity: ${e.message}")
        }
    }
}




















