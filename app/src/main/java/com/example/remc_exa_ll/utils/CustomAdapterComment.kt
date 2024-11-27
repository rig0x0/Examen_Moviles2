package com.example.remc_exa_ll.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remc_exa_ll.R
import com.example.remc_exa_ll.data.ApiComment


class CustomAdaptetComment(val comments: List<ApiComment>):
    RecyclerView.Adapter<CustomAdaptetComment.ViewHolder>() {

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    // Para detectar el click y longClick
    interface onItemClickListener {
        // Despues define el metodo
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    // Para que exterior se comunique con el interior
    // Este mediti si existe en el ListView - No Recycler
    fun onSetClickListener(listener: onItemClickListener){
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        // Referencia a los elementos de mi diseño
        val commentId = itemView.findViewById<TextView>(R.id.tvCommentId)
        val email = itemView.findViewById<TextView>(R.id.tvCommentEmail)
        val name = itemView.findViewById<TextView>(R.id.tvCommentName)
        val body = itemView.findViewById<TextView>(R.id.tvCommentBody)

        init {
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
        // Crear la vista inflate
        val vista: View =
            LayoutInflater.from(parent.context).inflate(R.layout.comments_list,parent,false)
        return ViewHolder(vista,miListener)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commentId.text = comments[position].id.toString()
        holder.email.text = comments[position].email
        holder.name.text = comments[position].name
        holder.body.text = comments[position].body
    }

}