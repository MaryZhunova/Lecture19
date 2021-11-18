package com.example.recipe.presentation.switchfavourites.addrecipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordtranslate.R
import android.net.Uri
import android.widget.LinearLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.example.wordtranslate.databinding.AddWordBinding
import com.example.wordtranslate.presentation.addword.viewmodel.SharedViewModel


class AddRecipeFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: AddWordBinding
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
        binding = AddWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.chooseIcon.setOnClickListener {
            observeLiveData()
            val viewGroup = FragmentContainerView(requireContext())
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            viewGroup.layoutParams = params
            viewGroup.id = R.id.fragment_id
            binding.root.addView(viewGroup)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_id, ChooseImageFragment.newInstance())
                .addToBackStack("fragment")
                .commit()
        }

        binding.add.setOnClickListener {
            mCallback.onClick(binding.keyword.text.toString(), binding.translation.text.toString(), uri.toString())
            activity?.supportFragmentManager?.popBackStack()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnItemClickListener
        } catch (e: Exception) {
            throw Exception()
        }
    }

    private fun observeLiveData() {
        viewModel.changeImage
            .observe(viewLifecycleOwner) { uri: Uri ->
                binding.previewIcon.setImageURI(uri)
                this.uri = uri
            }
    }

}

