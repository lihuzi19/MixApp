package com.lihuzi.takenotes.ui.viewholder

import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *@Author lining
 *@Date 2023/6/30
 *@DESC TODO
 **/
class MVVMViewModel : ViewModel() {

    val data = MutableLiveData<MutableList<String>>()

    val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> by lazy {
        object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ) = object : RecyclerView.ViewHolder(TextView(parent.context)) {}

            override fun getItemCount() = if (data.value != null) data.value!!.size else 0

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as TextView).text = data.value!![position]
            }

        }
    }


    fun getDataFromUnKnow() {
        GlobalScope.launch(Dispatchers.IO) {
            var list = arrayListOf<String>()
            for (i in 0 until 100) {
                list.add(i.toString())
            }
            launch(Dispatchers.Main) {
                data.value = list
                adapter.notifyDataSetChanged()
            }
        }
    }

}