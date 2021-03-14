package com.moanes.niceonetask.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.moanes.datasource.model.Character
import com.moanes.niceonetask.R
import com.squareup.picasso.Picasso

class CharactersAdapter :
    ListAdapter<Character, CharactersAdapter.ViewHolder>(CharacterItemDiffCallback()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)

        holder.name.text = character.name
//        holder.age.text = character.liveAge
        character.liveAge?.let {
            character.liveAge.observe(context as AppCompatActivity, {
                holder.age.text = it
            })
        }

        Picasso.get().load(character.img).into(holder.image)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.findViewById(R.id.name)
        val age: MaterialTextView = view.findViewById(R.id.age)
        val image: ShapeableImageView = view.findViewById(R.id.image)
    }

    class CharacterItemDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.charId == newItem.charId
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.equals(newItem)
        }
    }
}