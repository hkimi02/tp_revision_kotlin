package com.example.tp_review
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_review.R
import com.example.tp_review.User


internal class MyAdapter(private val dataSet: List<User>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.name)
        val email: TextView = itemView.findViewById(R.id.email)
        val adresse: TextView = itemView.findViewById(R.id.adresse)
        val birthdate: TextView = itemView.findViewById(R.id.birthdate)
        val userCard: CardView = itemView.findViewById(R.id.UserCard)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (selectedPosition == position) {
                    selectedPosition = RecyclerView.NO_POSITION
                    notifyDataSetChanged()
                } else {
                    selectedPosition = position
                    notifyDataSetChanged()
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.user_show, parent, false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = dataSet[position]
        holder.name.text = currentUser.name_user
        holder.email.text = currentUser.email
        holder.adresse.text = currentUser.adresse
        holder.birthdate.text = currentUser.birthdate

        if (selectedPosition == position) {
            holder.userCard.setBackgroundColor(Color.RED)
        } else {
            holder.userCard.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
