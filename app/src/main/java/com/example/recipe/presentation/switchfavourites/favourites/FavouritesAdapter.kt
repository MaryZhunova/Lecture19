package com.example.recipe.presentation.switchfavourites.favourites

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
class FavouritesAdapter(
    val recipes: MutableList<RecipePresentationModel>,
    private val onFavouriteRecipeClickListener: OnFavouriteRecipeClickListener
) : RecyclerView.Adapter<FavouritesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipes_list_info_item, parent, false),
            onFavouriteRecipeClickListener
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
                .error(R.drawable.no_image_logo)
                .into(holder.icon)
        }
        holder.recipeName.text = recipes[position].label
        holder.favouriteFilled.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class MyViewHolder(
        itemView: View,
        onFavouriteRecipeClickListener: OnFavouriteRecipeClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val icon: ImageView = itemView.findViewById(R.id.preview_icon)
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        val favouriteFilled: ImageView = itemView.findViewById(R.id.fav_filled)
        private val onRecipeClick = onFavouriteRecipeClickListener

        init {
            itemView.setOnClickListener(this)
            favouriteFilled.setOnClickListener(this)
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
                if (v.id == favouriteFilled.id) {
                    val position = adapterPosition
                    onRecipeClick.onFavouriteClick(recipes[position])
                    recipes.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    onRecipeClick.onRecipeClick(recipes[adapterPosition])
                }
            }
        }
    }
}

/**
 * Интерфейс слушателя клика на элемент списка
 */

interface OnFavouriteRecipeClickListener {
    /**
     * Обработка нажатия на элемент списка
     *
     * @param recipePresentationModel элемент из списка
     */
    fun onRecipeClick(recipePresentationModel: RecipePresentationModel)

    /**
     * Обработка нажатия на imageview "favorite" элемента списка
     *
     * @param recipePresentationModel элемент из списка
     */
    fun onFavouriteClick(recipePresentationModel: RecipePresentationModel)
}