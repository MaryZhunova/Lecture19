package com.example.recipe.view.recipesinfo

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.data.model.Recipe
import com.example.recipe.utils.GlideApp

/**
 * Адаптер для отображения элементов списка
 */
class RecipesInfoAdapter(private val recipes: List<Recipe>, private val onRecipeClickListener: OnRecipeClickListener): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipes_list_info_rv, parent, false), onRecipeClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tempUri = Uri.parse(recipes[position].image)
        GlideApp.with(holder.itemView.context)
            .asBitmap()
            .load(tempUri)
            .into(holder.icon)
        holder.recipeName.text = recipes[position].label
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

class MyViewHolder(itemView: View, onRecipeClickListener: OnRecipeClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val icon: ImageView = itemView.findViewById(R.id.preview_icon)
    val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
    private val onRecipeClick = onRecipeClickListener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onRecipeClick.onRecipeClick(adapterPosition)
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
}