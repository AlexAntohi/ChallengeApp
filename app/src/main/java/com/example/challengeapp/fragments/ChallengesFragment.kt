package com.example.challengeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.R
import com.example.challengeapp.adapters.ChallengeAdapter
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.models.Challenge

class ChallengesFragment : Fragment() {
    private val challengeRepository = ChallengeRepository()

    private val challengesList by lazy {
        ArrayList<Challenge>()
    }
    private var challengeAdapter: ChallengeAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews(view)
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setupViews(view:View) {
        setupRecyclerView(view)
        getChallenges()
    }
    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        challengeAdapter = ChallengeAdapter(challengesList)
        recyclerView.adapter = challengeAdapter
    }

    private fun getChallenges() {
        val onGetListener = object : ChallengeRepository.OnGetListener {
            override fun onSuccess(items: List<Challenge>) {
                challengesList.clear()
                items.forEach{challenge->
                    challengesList.add(challenge)
                }
                challengeAdapter?.notifyItemRangeChanged(0, challengesList.size)
            }
        }
        challengeRepository.getAllChallenges(onGetListener)
    }
}
