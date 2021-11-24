package com.example.recipe.presentation.switchfavourites.myrecipes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.models.presentation.MyRecipePresentationModel

/**
 * Адаптер для отображения элементов списка
 */
class MyRecipesAdapter(private val onMyRecipeClickListener: OnMyRecipeClickListener) : RecyclerView.Adapter<MyRecipesAdapter.MyViewHolder>() {

    var recipes: MutableList<MyRecipePresentationModel> = mutableListOf()
    var onItemClickListener: ((MyRecipePresentationModel) -> Unit)? = null

    fun updateList(recipes: MutableList<MyRecipePresentationModel>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

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
//        if (PreferenceManager.getDefaultSharedPreferences(holder.getContext())
//                .getBoolean("switch_images", true)
//        ) {
//            val tempUri = Uri.parse(recipes[position].image)
//            GlideApp.with(holder.getContext())
//                .asBitmap()
//                .load(tempUri)
//                .error(R.drawable.no_image_logo)
//                .into(holder.icon)
//        }
        holder.recipeName.text = recipes[position].recipeName
    }

    override fun getItemCount(): Int {
        return recipes.size
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
                    onRecipeClick.onDeleteClick(recipes[adapterPosition])
                    recipes.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    onItemClickListener?.invoke(recipes[adapterPosition])
                }
            }
        }
    }
}

/**
 * Интерфейс слушателя клика на элемент списка
 */

interface OnMyRecipeClickListener {
    /**
     * Обработка нажатия на imageview "delete" элемента списка
     *
     * @param recipePresentationModel элемент из списка
     */
    fun onDeleteClick(recipePresentationModel: MyRecipePresentationModel)
}