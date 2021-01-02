package com.ravitej.coroutinessample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ravitej.coroutinessample.R
import com.ravitej.coroutinessample.databinding.UserItemBinding
import com.ravitej.coroutinessample.model.User


/**
 * RecyclerView's Helper class to create ViewHolders and to bind data to the ViewHolder at a given position.
 *
 * @ListAdapter - It is RecyclerView.Adapter base class for presenting List data in a RecyclerView,
 * including computing diffs between Lists on a background thread.
 *
 * @DiffUtil.ItemCallback<T> - It provides a way to calculate difference between the two list
 * and calls related methods on the adapter like
 * notifyItemInserted(), notifyItemRemoved(), notifyItemChanged(), etc.
 * As a result, the whole list doesn’t get refreshed.
 * Only the items that have been changed are refreshed.
 * It also animates the item changes a little bit so it looks quite nice and it is also performance efficient.
 */
class UserAdapter(diff: DiffUtil.ItemCallback<User> = Companion) :
    ListAdapter<User, UserAdapter.UserViewHolder>(diff) {

    private lateinit var binding: UserItemBinding
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_item,
            parent,
            false
        )

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(holder, currentList[position])
        holder.binding.executePendingBindings()
        setAnimation(holder.binding.root, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(
                    viewToAnimate.context,
                    android.R.anim.slide_in_left
                )
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    inner class UserViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(holder: UserViewHolder, user: User) {
            holder.binding.user = user
        }
    }

    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            (oldItem.firstName == newItem.firstName && oldItem.lastName == oldItem.lastName)
    }
}