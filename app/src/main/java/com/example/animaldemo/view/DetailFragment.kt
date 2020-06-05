package com.example.animaldemo.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.session.PlaybackState
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.example.animaldemo.R
import com.example.animaldemo.databinding.FragmentDetailBinding
import com.example.animaldemo.model.Animal
import com.example.animaldemo.model.PaleteColor
import com.example.animaldemo.utils.getProgressDrable
import com.example.animaldemo.utils.loadImage


class DetailFragment : Fragment() {

    var animal:Animal?=null
    private lateinit var dataBinding:FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            animal=DetailFragmentArgs.fromBundle(it).animal
        }
//        context?.let {
//            dataBinding.animalImage.loadImage(animal?.imageUrl, getProgressDrable((it)))
//        }
//        animalName.text=animal?.name
//        animalLoacation.text=animal?.location
//        animalLifeSpan.text=animal?.lifeSpan
//        animalDiet.text=animal?.diet
        animal?.imageUrl?.let{
            setBackgroundColor(it)
        }
        dataBinding.animal=animal


    }
    fun setBackgroundColor(url:String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object :CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate(){
                            val intColor=it?.lightMutedSwatch?.rgb?:0
                            //dataBinding.detailScreen.setBackgroundColor(intColor)
                            dataBinding.paletecolor= PaleteColor(intColor)
                        }
                }

            })

            }
    }

