package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devarthur4718.searchaddressapp.databinding.ItemAddressBinding
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress

open class AddressListAdapter :
    ListAdapter<LocalAddress, AddressListAdapter.AddressListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AddressListViewHolder(ItemAddressBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AddressListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AddressListViewHolder(
        private val itemBinding: ItemAddressBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: LocalAddress) {
            itemBinding.run {
                tvAddressName.text = data.localName
                tvPostalCode.text = data.postalCode
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalAddress>() {
            override fun areItemsTheSame(oldItem: LocalAddress, newItem: LocalAddress): Boolean {
                return oldItem.postalCode == newItem.postalCode
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: LocalAddress, newItem: LocalAddress): Boolean {
                return oldItem == newItem
            }
        }
    }
}
