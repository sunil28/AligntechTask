package com.aligntechtask.musicalbum.ui.album

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.request.RequestOptions
import com.aligntechtask.musicalbum.BuildConfig
import com.aligntechtask.musicalbum.R
import com.aligntechtask.musicalbum.data.Album
import com.aligntechtask.musicalbum.data.source.RemoteDataSource
import com.aligntechtask.musicalbum.databinding.FragmentAlbumDetailBinding
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream
import java.util.*


class AlbumDetailFragment
constructor(
    val requestOptions: RequestOptions,
    val dataSource: RemoteDataSource
): Fragment(){

    private val TAG: String = "AppDebug"

    private lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            args.getParcelable<Album>("album").let{album = args.getParcelable<Album>("album")!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAlbumDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_album_detail, container, false)
binding.album = album
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
share_layout.setOnClickListener(View.OnClickListener { v ->
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Music Album")
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage ="""${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}""".trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Choose one"))
    } catch (e: Exception) {
       e.printStackTrace()
    }
})

        addto_layout.setOnClickListener(View.OnClickListener { v ->
            val url:String = album.image
            val imageRequest:ImageRequest = ImageRequest(
                url,
                Response.Listener<Bitmap> { bitmap->
                    try {
                        val root: String = Environment.getExternalStorageDirectory().toString();
                        val newDir = File(root + "/Photos");
                        newDir.mkdirs();
                        val gen = Random();
                        var n: Int = 10000;
                        n = gen.nextInt(n);
                        val fotoname: String = "Photo-" + n + ".jpg";
                        val file = File(newDir, fotoname);
                        if (!file.exists())
                            Toast.makeText(
                                requireContext(),
                                "File not created..",
                                Toast.LENGTH_SHORT
                            ).show();
                        try {
                            val out = FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                            Toast.makeText(
                                requireContext(),
                                "Added to Photos",
                                Toast.LENGTH_SHORT
                            ).show();
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                0,0, ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565,

                Response.ErrorListener { volleyError -> Toast.makeText(requireContext(), volleyError.message, Toast.LENGTH_LONG).show() })

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add<Bitmap>(imageRequest)

        })
      //  setMovieDetails()

copy_layout.setOnClickListener(View.OnClickListener { v->
    Toast.makeText(
        requireContext(),
        "Item Copied",
        Toast.LENGTH_SHORT
    ).show();
})
    }



}

















