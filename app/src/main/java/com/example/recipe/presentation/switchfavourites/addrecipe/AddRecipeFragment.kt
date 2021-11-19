package com.example.recipe.presentation.switchfavourites.addrecipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.net.Uri
import com.example.recipe.databinding.AddRecipeBinding


class AddRecipeFragment : Fragment() {
    private lateinit var binding: AddRecipeBinding
    private lateinit var mCallback: OnItemClickListener
    private var uri: Uri? = null

    interface OnItemClickListener {
        fun onClick(keyword: String, translation: String, uri: String?)
    }

    companion object {
        fun newInstance(): AddRecipeFragment {
            return AddRecipeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnItemClickListener
        } catch (e: Exception) {
            throw Exception()
        }
    }
}

