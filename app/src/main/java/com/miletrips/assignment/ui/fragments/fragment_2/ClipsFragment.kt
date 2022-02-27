package com.miletrips.assignment.ui.fragments.fragment_2

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.miletrips.assignment.databinding.FragmentClipsBinding
import com.miletrips.assignment.models.Result
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PARAM = "param"

@AndroidEntryPoint
class ClipsFragment : Fragment() {
    private var clip: Result? = null
    private var _binding: FragmentClipsBinding? = null
    private val binding get() = _binding!! // Helper Property


    @Inject
    lateinit var exoPlayer: ExoPlayer

    @Inject
    lateinit var mediaSourceFactory: ProgressiveMediaSource.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clip = it.getSerializable(ARG_PARAM) as Result
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentClipsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.videoFragmentPlayerView.apply {
            this.player = exoPlayer
//            this.controllerAutoShow = false
        }
    }

    companion object {

        @JvmStatic fun newInstance(param: Result) =
                ClipsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM, param)
                    }
                }
    }

    private fun initPlayer() {
        val videoSource = mediaSourceFactory.createMediaSource(MediaItem.fromUri(Uri.parse(clip?.video)))
        exoPlayer.setMediaSource(videoSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = false
    }

    private fun startPlayer() {
        exoPlayer.playWhenReady = true
    }

    private fun pausePlayer() {
        exoPlayer.playWhenReady = false
        exoPlayer.seekTo(0)
    }

    private fun stopPlayer() {
        exoPlayer.stop()
    }

    private fun destroyPlayer() {
        exoPlayer.release()
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onResume() {
        super.onResume()
        startPlayer()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyPlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}