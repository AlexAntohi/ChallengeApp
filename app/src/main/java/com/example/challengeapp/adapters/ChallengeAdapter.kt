package com.example.challengeapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.R
import com.example.challengeapp.data.models.Challenge

class ChallengeAdapter (val challengeList: ArrayList<Challenge>): RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.challenge, parent, false)
        val challengeViewHolder = ChallengeViewHolder(view)

        Log.e("ChallengeAdapter", "onCreateViewHolder")

        return challengeViewHolder
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge: Challenge = challengeList[position]
        holder.bind(challenge)
        Log.e("ChallengeAdapter", "onBindViewHolder: $position")
    }

    override fun getItemCount(): Int {
        return this.challengeList.size
    }

    inner class ChallengeViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val title: TextView
        private val description: TextView
        init{
            title=view.findViewById<TextView>(R.id.animal)
            description=view.findViewById<TextView>(R.id.continent)
        }
        fun bind(challenge: Challenge)
        {
            title.text = challenge.name
            description.text = challenge.description
        }
    }
}