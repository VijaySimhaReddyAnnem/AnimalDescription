package com.example.animaldemo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.animaldemo.R
import com.example.animaldemo.databinding.ListItemBinding
import com.example.animaldemo.model.Animal
import com.example.animaldemo.utils.getProgressDrable
import com.example.animaldemo.utils.loadImage
import kotlinx.android.synthetic.main.list_item.view.*

class AnimalListAdapter(private var animalList:ArrayList<Animal>):RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(),AnimalClickListener {

    fun updateAnimalList(newAnimalLIst:List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalLIst)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflator=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<ListItemBinding>(inflator,R.layout.list_item,parent,false)
        return AnimalViewHolder(view)

    }

    override fun getItemCount()=animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
       holder.view.animal=animalList[position]
        holder.view.listener=this
//        holder.view.animalLayout.setOnClickListener {
//            val action=List_FragmentDirections.listToDetailFragment(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }

    }
    class AnimalViewHolder(var view: ListItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onClick(v: View) {
        for(animal in animalList){
            if(v.tag==animal.name){
                val action=List_FragmentDirections.listToDetailFragment(animal)
            Navigation.findNavController(v).navigate(action)
            }
        }
    }

}