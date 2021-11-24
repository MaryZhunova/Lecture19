package com.example.recipe.presentation.switchfavourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.recipe.R
import com.example.recipe.databinding.MyRecipeDetailBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.utils.GlideApp

class MyRecipeDetailFragment : Fragment() {
    private lateinit var binding: MyRecipeDetailBinding
    private var recipe: MyRecipePresentationModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = arguments?.getParcelable(RECIPE)

//        if (PreferenceManager.getDefaultSharedPreferences(requireContext())
//                .getBoolean("switch_images", true)
//        ) {
//            val tempUri = Uri.parse(recipe?.image)
//            GlideApp.with(requireContext())
//                .asBitmap()
//                .load(tempUri)
//                .error(R.drawable.no_image_logo)
//                .into(binding.image)
//        }

        binding.name.text = recipe?.recipeName ?: ""
        binding.ingredients.text = recipe?.ingredients ?: ""
        binding.directions.text = recipe?.instructions ?: ""
    }


    companion object {
        private  const val RECIPE = "recipe"
        fun newInstance(recipe: MyRecipePresentationModel): MyRecipeDetailFragment {
            return MyRecipeDetailFragment().apply {
                arguments = bundleOf(RECIPE to recipe)
            }
        }
    }
}

