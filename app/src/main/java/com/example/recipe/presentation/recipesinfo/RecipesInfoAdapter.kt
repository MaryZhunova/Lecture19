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
class RecipesInfoAdapter(private val recipes: List<RecipePresentationModel>, private val onRecipeClickListener: OnRecipeClickListener): RecyclerView.Adapter<MyViewHolder>()
//    , Filterable
    {

//    var recipesCopy: List<*>? = null

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
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun publishResults(constraint: CharSequence, results: FilterResults) {
//                recipesCopy = results.values as List<*>
//                notifyDataSetChanged()
//            }
//
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                val filteredResults: List<*> = if (constraint.isEmpty()) {
//                    recipes
//                } else {
//                    getFilteredResults(constraint.toString().lowercase(Locale.getDefault()))
//                }
//                val results = FilterResults()
//                results.values = filteredResults
//                return results
//            }
//        }
//    }
//
//    fun getFilteredResults(constraint: String): List<RecipePresentationModel> {
//        val results = mutableListOf<RecipePresentationModel>()
//        for (item in recipes) {
//            if (item.label.lowercase(Locale.getDefault()).contains(constraint)) {
//                results.add(item)
//            }
//        }
//        return results
//    }
}

class MyViewHolder(itemView: View, onRecipeClickListener: OnRecipeClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val icon: ImageView = itemView.findViewById(R.id.preview_icon)
    val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
    private val favourite: ImageView = itemView.findViewById(R.id.fav)
    private val favouriteFilled: ImageView = itemView.findViewById(R.id.fav_filled)
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