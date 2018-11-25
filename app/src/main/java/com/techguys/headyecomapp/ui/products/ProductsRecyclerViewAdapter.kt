package com.techguys.headyecomapp.ui.products


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.Product
import com.techguys.headyecomapp.databinding.RowProductsListBinding
import com.techguys.headyecomapp.ui.common.RecyclerViewOnItemClickListener

class ProductsRecyclerViewAdapter :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductsRecyclerViewViewHolder>() {

    private val products = mutableListOf<Product>()
    private var inflater: LayoutInflater? = null
    var recyclerViewOnItemClickListener: RecyclerViewOnItemClickListener? = null

    fun clear() = products.clear()

    fun addData(categories: List<Product>) {
        this.products.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsRecyclerViewViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding =
            DataBindingUtil.inflate<RowProductsListBinding>(inflater!!, R.layout.row_products_list, parent, false)
        return ProductsRecyclerViewViewHolder(recyclerViewOnItemClickListener, binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductsRecyclerViewViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun getItem(position: Int) = products[position]

    inner class ProductsRecyclerViewViewHolder(
        private val recyclerViewOnItemClickListener: RecyclerViewOnItemClickListener?,
        private val viewBinding: RowProductsListBinding
    ) :
        RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener{
                recyclerViewOnItemClickListener?.onItemClick(adapterPosition)
            }
        }

        fun bind(product: Product) {
            viewBinding.product = product
            viewBinding.executePendingBindings()
        }
    }
}



