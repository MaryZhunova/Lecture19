package com.example.recipe.presentation.addrecipe.chooseimage

import android.app.Activity
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
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.recipe.databinding.FragmentChooseImageBinding

/**
 * Фрагмент для отображения изображений устройства, доступных для выбора
 */
class ChooseImageFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentChooseImageBinding
    private lateinit var mCallback: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(uri: Uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnItemClickListener
        } catch (e: Exception) {
            throw Exception("$context should implement ChooseImageFragment.OnItemClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()

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
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                uriList.add(
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )
                )
            }
        }
        return uriList
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
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

