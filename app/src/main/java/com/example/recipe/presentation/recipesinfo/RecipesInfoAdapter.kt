package com.example.recipe.presentation.recipesinfo

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.GlideApp

/**
 * Адаптер для отображения элементов списка
 */
class RecipesInfoAdapter(private val recipes: List<RecipePresentationModel>, private val onRecipeClickListener: OnRecipeClickListener): RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipes_list_info_item, parent, false), onRecipeClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (PreferenceManager.getDefaultSharedPreferences(holder.getContext()).getBoolean("switch_images", true)) {
            val tempUri = Uri.parse(recipes[position].image)
            GlideApp.with(holder.getContext())
                .asBitmap()
                .load(tempUri)
                .into(holder.icon)
        }
        holder.recipeName.text = recipes[position].label
        if (recipes[position].isFavourite) {
            holder.favouriteFilled.visibility = View.VISIBLE
        } else {
            holder.favouriteFilled.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

class MyViewHolder(itemView: View, onRecipeClickListener: OnRecipeClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val icon: ImageView = itemView.findViewById(R.id.preview_icon)
    val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
    val favouriteFilled: ImageView = itemView.findViewById(R.id.fav_filled)
    private val favourite: ImageView = itemView.findViewById(R.id.fav)
    private val onRecipeClick = onRecipeClickListener

    init {
        itemView.setOnClickListener(this)
        favourite.setOnClickListener(this)
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
            if (v.id == favourite.id || v.id == favouriteFilled.id) {
                if (!favouriteFilled.isVisible) {
                    favouriteFilled.visibility = View.VISIBLE
                    onRecipeClick.onFavouriteClick(adapterPosition, true)
                } else {
                    favouriteFilled.visibility = View.INVISIBLE
                    onRecipeClick.onFavouriteClick(adapterPosition, false)
                }
            } else {
                onRecipeClick.onRecipeClick(adapterPosition)
            }
        }
    }
}

/**
 * Интерфейс слушателя клика на элемент списка
 */

interface OnRecipeClickListener {
    /**
     * Обработка нажатия на элемент списка
     *
     * @param position позиция элемента в списке
     */
    fun onRecipeClick(position: Int)
    /**
     * Обработка нажатия на imageview "favorite" элемента списка
     *
     * @param position позиция элемента в списке
     */
    fun onFavouriteClick(position: Int, pressed: Boolean)
}