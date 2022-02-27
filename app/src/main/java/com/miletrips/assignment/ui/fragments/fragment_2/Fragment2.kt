package com.miletrips.assignment.ui.fragments.fragment_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.miletrips.assignment.databinding.Fragment2Binding
import com.miletrips.assignment.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Fragment2 : Fragment() {
    private val viewmodel: MainViewModel by activityViewModels()
    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!! // Helper Property

    private lateinit var adapter: ClipsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment2Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ClipsAdapter(this)

        binding.apply {
            clipsViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            clipsViewPager.adapter = adapter
            clipsViewPager.offscreenPageLimit = 1
        }

        viewmodel.response.observe(viewLifecycleOwner) { result ->
            adapter.differ.submitList(result)
        }

        viewmodel.getAllClips()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}