package com.aligntechtask.musicalbum.ui.album

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bumptech.glide.request.RequestOptions
import com.aligntechtask.musicalbum.R
import com.aligntechtask.musicalbum.data.Album
import com.aligntechtask.musicalbum.data.source.Category
import com.aligntechtask.musicalbum.data.source.RemoteDataSource
import com.aligntechtask.musicalbum.factory.MovieFragmentFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumDetailFragmentTest{

    @Test
    fun test_recreateActivity() {

        // SETUP
        val albumId = "1"
        val Category_pos = 0
        val title = "My Turn"
        val description = "A tough aspiring chef is hired to bring home a mobster's son from the Amazon but " +
                "becomes involved in the fight against an oppressive town operator and the search " +
                "for a legendary treasure."
        val album = Album(
            albumId,
            title,
            "https://is2-ssl.mzstatic.com/image/thumb/Music124/v4/dd/25/8c/dd258c8a-f804-785f-1268-ad3cac0db873/20UMGIM04591.rgb.jpg/200x200bb.png",
            description

        )
        val fragmentFactory = MovieFragmentFactory(null, null)
        val bundle = Bundle()
        bundle.putParcelable("album", album)
        val scenario = launchFragmentInContainer<AlbumDetailFragment>(
            fragmentArgs = bundle,
            factory = fragmentFactory
        )


        // VERIFY
        onView(withId(R.id.movie_title)).check(matches(withText(title)))



    }

    @Test
    fun test_isMovieDataVisible()
    {

    }

}



















