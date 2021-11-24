package com.example.recipe.presentation.switchfavourites.addrecipe

import android.content.ContentUris
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.recipe.databinding.ChooseImageBinding
import com.example.wordtranslate.presentation.addword.view.ImagesAdapter
import com.example.wordtranslate.presentation.addword.view.OnItemClickListener

class ChooseImageFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: ChooseImageBinding
    private lateinit var mCallback: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(uri: Uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnItemClickListener
        } catch (e: Exception) {
            throw Exception("$context should implement MyRecipesFragment.OnItemClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChooseImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gallery = getPhotos()
        if (gallery.isEmpty()) {
            Toast.makeText(context, "Gallery is empty", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.popBackStack()
        } else {
            showData(gallery)
        }

    }


    private fun showData(list: List<Uri>) {
        val adapter = ImagesAdapter(list, this)
        binding.recyclerView.adapter = adapter
    }

    private fun getPhotos(): List<Uri> {
        val uriList = mutableListOf<Uri>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"
        val query = activity?.contentResolver?.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            Log.e("fucking shit", cursor.count.toString())
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                uriList.add(ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id)
                )
            }
        }
        return uriList
    }

    override fun onClick(uri: Uri) {
        mCallback.onClick(uri)
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {
        fun newInstance(): ChooseImageFragment {
            return ChooseImageFragment()
        }
    }

}

