package com.example.remc_exa_ll.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remc_exa_ll.R
import com.example.remc_exa_ll.data.ApiUser

class CustomAdaptetUser(val users: List<ApiUser>) : RecyclerView.Adapter<CustomAdaptetUser.ViewHolder>() {

    private lateinit var miListener: onItemClickListener

    // Interfaz para manejar el click y long click
    interface onItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    // Configurar el listener para clicks
    fun onSetClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    // ViewHolder para manejar las vistas del item
    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        // Referencia a los TextViews de tu layout
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvUserEmail: TextView = itemView.findViewById(R.id.tvUserEmail)
        //val tvUserPhone: TextView = itemView.findViewById(R.id.tvUserPhone)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvCompany: TextView = itemView.findViewById(R.id.tvCompany)

        init {
            // Detectar click y long click
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                listener.onItemLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflar el layout del item
        val vista: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Construir las cadenas para los TextViews
        val user = users[position]
        val address = "${user.address.suite}, ${user.address.street}, ${user.address.zipcode}, ${user.address.city}, Geo-localización:(${user.address.geo.lat}, ${user.address.geo.lng})"
        val company = "${user.company.name}, ${user.company.catchPhrase}, ${user.company.bs}"

        // Asignar los valores a los TextViews
        holder.tvUserName.text = user.name
        holder.tvUserEmail.text = user.email
        //holder.tvUserPhone.text = user.phone
        holder.tvAddress.text = address
        holder.tvCompany.text = company
    }
}
