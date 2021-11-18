package com.example.recipe.presentation.switchfavourites.myrecipes

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.GlideApp

/**
 * Адаптер для отображения элементов списка
 */
class MyRecipesAdapter(
    val recipes: MutableList<RecipePresentationModel>,
    private val onMyRecipeClickListener: OnMyRecipeClickListener
) : RecyclerView.Adapter<MyRecipesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_recipes_item, parent, false),
            onMyRecipeClickListener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (PreferenceManager.getDefaultSharedPreferences(holder.getContext())
                .getBoolean("switch_images", true)
        ) {
            val tempUri = Uri.parse(recipes[position].image)
            GlideApp.with(holder.getContext())
                .asBitmap()
                .load(tempUri)
                .into(holder.icon)
        }
        holder.recipeName.text = recipes[position].label
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class MyViewHolder(itemView: View, onMyRecipeClickListener: OnMyRecipeClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val icon: ImageView = itemView.findViewById(R.id.preview_icon)
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        private val onRecipeClick = onMyRecipeClickListener

        init {
            itemView.setOnClickListener(this)
        }

        /**
         * Получить контекст view
         *
         * @return контекст view
         */
        fun getContext(): Context {
            return itemView.context
        }

        override fun onClick(v: View?) {
            if (v != null) {
                onRecipeClick.onRecipeClick(recipes[adapterPosition])
            }
        }
    }
}

/**
 * Интерфейс слушателя клика на элемент списка
 */

interface OnMyRecipeClickListener {
    /**
     * Обработка нажатия на элемент списка
     *
     * @param recipePresentationModel элемент из списка
     */
    fun onRecipeClick(recipePresentationModel: RecipePresentationModel)
}