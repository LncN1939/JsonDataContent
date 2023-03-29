package com.example.evaluacionjson_leocorea.ui.see


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacionjson_leocorea.databinding.UserItemBinding

class UserAdapter (private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun load(item: User){
            binding.idCtv.text = item.id
            binding.nombrestv.text = item.name
            binding.apellidostv.text = item.secondName
            binding.fechaNactv.text = item.dateBorn
            binding.titulotv.text = item.tittle
            binding.emailtv.text = item.email
            binding.facultadtv.text = item.faculty
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.load(this.userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}