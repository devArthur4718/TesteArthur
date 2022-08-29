package com.example.testforxml

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testforxml.databinding.ItemAddressBinding
import com.example.testforxml.feature_address_search.domain.model.LocalAddress

open class AddressListAdapter :
    RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder>(), Filterable {

    var addressList = mutableListOf<LocalAddress>()
    var addressListFiltered = mutableListOf<LocalAddress>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AddressListViewHolder(ItemAddressBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AddressListViewHolder, position: Int) {
        holder.bind(addressListFiltered[position])
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

    fun addData(list: MutableList<LocalAddress>) {
        addressList = list
        addressListFiltered = addressList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = addressListFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""

                if (charString.isEmpty()) {
                    addressListFiltered = addressList
                } else {
                    val filteredList = mutableListOf<LocalAddress>()
                    addressList.filter {
                        it.localName.contains(constraint!!) or it.postalCode.contains(
                            constraint!!
                        )
                    }
                        .forEach { filteredList.add(it) }
                    addressListFiltered = filteredList
                }
                return FilterResults().apply { values = addressListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                addressListFiltered = if (results?.values == null) {
                    ArrayList()
                } else {
                    results.values as MutableList<LocalAddress>
                }
                notifyDataSetChanged()
            }
        }
    }
}
