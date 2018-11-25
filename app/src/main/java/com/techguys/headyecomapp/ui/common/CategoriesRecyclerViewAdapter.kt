package com.techguys.headyecomapp.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.Category
import com.techguys.headyecomapp.databinding.RowCategoriesListBinding

class CategoriesRecyclerViewAdapter :
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CategoriesRecyclerViewViewHolder>() {

    private val categories = mutableListOf<Category>()
    private var inflater: LayoutInflater? = null
    var recyclerViewOnItemClickListener: RecyclerViewOnItemClickListener? = null

    fun clear() = categories.clear()

    fun addData(categories: List<Category>) {
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesRecyclerViewViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding =
            DataBindingUtil.inflate<RowCategoriesListBinding>(inflater!!, R.layout.row_categories_list, parent, false)
        return CategoriesRecyclerViewViewHolder(recyclerViewOnItemClickListener, binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoriesRecyclerViewViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun getItem(position: Int) = categories[position]

    inner class CategoriesRecyclerViewViewHolder(
        private val recyclerViewOnItemClickListener: RecyclerViewOnItemClickListener?,
        private val viewBinding: RowCategoriesListBinding
    ) :
        RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener{
                recyclerViewOnItemClickListener?.onItemClick(adapterPosition)
            }
        }

        fun bind(category: Category) {
            viewBinding.category = category
            viewBinding.executePendingBindings()
        }
    }
}