package com.example.recipe.presentation.switchfavourites.myrecipes

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.recipesinfo.RecipesInfoAdapter
import com.example.recipe.utils.GlideApp

/**
 * Адаптер для отображения элементов списка
 */
class MyRecipesAdapter(private val onMyRecipeClickListener: OnMyRecipeClickListener) : ListAdapter<MyRecipePresentationModel, MyRecipesAdapter.MyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_recipes_item, parent, false), onMyRecipeClickListener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (PreferenceManager.getDefaultSharedPreferences(holder.getContext())
                .getBoolean("switch_images", true)
        ) {
            val tempUri = Uri.parse(currentList[position].image)
            GlideApp.with(holder.getContext())
                .asBitmap()
                .load(tempUri)
                .error(R.drawable.no_image_logo)
                .into(holder.icon)
        }
        holder.recipeName.text = currentList[position].recipeName
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class MyViewHolder(itemView: View, onMyRecipeClickListener: OnMyRecipeClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val icon: ImageView = itemView.findViewById(R.id.preview_icon)
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        private val delete: ImageView = itemView.findViewById(R.id.delete)
        private val onRecipeClick = onMyRecipeClickListener

        init {
            itemView.setOnClickListener(this)
            delete.setOnClickListener(this)
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
                if (v.id == delete.id) {
                    val position = adapterPosition
                    onRecipeClick.onDeleteClick(currentList[adapterPosition])
                    currentList.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    onRecipeClick.onRecipeClick(currentList[adapterPosition])
                }
            }
        }
    }
    private class DiffCallback : DiffUtil.ItemCallback<MyRecipePresentationModel>() {

        override fun areItemsTheSame(oldItem: MyRecipePresentationModel, newItem: MyRecipePresentationModel) =
            oldItem.recipeName == newItem.recipeName

        override fun areContentsTheSame(oldItem: MyRecipePresentationModel, newItem: MyRecipePresentationModel) =
            oldItem == newItem
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
    fun onRecipeClick(recipePresentationModel: MyRecipePresentationModel)

    /**
     * Обработка нажатия на imageview "delete" элемента списка
     *
     * @param recipePresentationModel элемент из списка
     */
    fun onDeleteClick(recipePresentationModel: MyRecipePresentationModel)
}