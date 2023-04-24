package com.example.araccessories.ui.features.homeFragment.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products
import com.example.araccessories.databinding.ProductItemBinding
import android.view.LayoutInflater
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.example.araccessories.R
import com.example.araccessories.ui.features.homeFragment.HomeFragment
import com.example.araccessories.ui.features.homeFragment.HomeFragmentDirections
import com.example.araccessories.ui.features.mainNavigation.MainNavigationDirections
import kotlinx.android.synthetic.main.product_item.view.*


class ProductsRecyclerViewAdapter :
    ListAdapter<Products, ProductsRecyclerViewAdapter.ProductViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
    inner class ProductViewHolder constructor(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind (product :Products){
                binding.product=product
                binding.productName.text=product.productName
                binding.productImage.setImageResource(product.productImage[0])
                if (product.productFav==0){
                    binding.productFav.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                else {
                    binding.productFav.setImageResource(R.drawable.baseline_favorite_24)

                }
                binding.productFav.setOnClickListener {
                    if (product.productFav==0){
                        binding.productFav.setImageResource(R.drawable.baseline_favorite_border_24)
                        product.productFav=1
                    }
                    else {
                        binding.productFav.setImageResource(R.drawable.baseline_favorite_24)
                        product.productFav=0

                    }
                }
                binding.productPrice.text = "${product.productPrice} Egp"
               // binding.productImage.setImageResource(product.productImage[0])
                binding.productRate.numStars=product.productRate.toInt()

                binding.root.product_item.setOnClickListener {
                    val action =
                        MainNavigationDirections.actionMainNavigationToProductDetailsFragment(
                            getItem(position)
                        )

                    binding.root.findNavController()
                        .navigate(action)

                }
                binding.executePendingBindings()
            }
        private fun onClickFav(){

        }

        init {

        }

    }
    fun from(parent: ViewGroup): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }
}
val diffCallback = object : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.productId == newItem.productId


    }


}