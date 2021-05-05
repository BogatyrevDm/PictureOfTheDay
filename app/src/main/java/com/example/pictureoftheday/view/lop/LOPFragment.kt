package com.example.pictureoftheday.view.lop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureoftheday.databinding.FragmentLopBinding
import com.example.pictureoftheday.model.lop.Planet
import com.example.pictureoftheday.utils.getListOfPlanets

class LOPFragment : Fragment() {
    private var _binding: FragmentLopBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var adapter = RecyclerViewNotesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLopBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        adapter.setDataSource(
            getListOfPlanets()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        recyclerView = binding.notesRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LOPFragment()
    }
}