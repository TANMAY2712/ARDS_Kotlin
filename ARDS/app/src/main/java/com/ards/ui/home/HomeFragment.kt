package com.ards.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ards.databinding.FragmentHomeBinding
import com.ards.ui.history.adapter.HistoryAdapter
import com.ards.ui.history.adapter.RecentAdapter
import com.ards.ui.history.model.Recent

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recentAdapter: RecentAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recentTrainsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val trainList = listOf(
            Recent("Sabarmati Express", "12578", "DELHI", "11:00", "BHOPAL", "23:35", 2),
            Recent("Ernakulam Express", "12911", "DELHI", "17:50", "ERNAKULAM", "22:15", 1),
            Recent("Rajdhani Express", "12578", "DELHI", "17:00", "BHUBANESHWAR", "21:35", 2),
            Recent("Kranti Express", "12911", "DELHI", "17:50", "KAHIPUR", "22:15", 1),
            Recent("Avadh Express", "12578", "DELHI", "11:00", "BAREILLY", "23:35", 1)
        )

        recentAdapter = RecentAdapter(trainList)
        binding.recentTrainsRecyclerView.adapter = recentAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}