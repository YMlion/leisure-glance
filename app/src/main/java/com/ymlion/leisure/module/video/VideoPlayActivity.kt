package com.ymlion.leisure.module.video

import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ymlion.leisure.R
import com.ymlion.leisure.base.BaseActivity
import com.ymlion.leisure.net.Http
import com.ymlion.leisure.util.SubscriberAdapter


class VideoPlayActivity : BaseActivity() {

    var mPlayerView: SimpleExoPlayerView? = null
    var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
    }

    override fun initView() {
        mPlayerView = exo_video
    }

    override fun initData() {
        // 1. Create a default TrackSelector
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        mPlayerView?.player = player
        Http.build().getVideoUrl(intent.getLongExtra("id", 0)).subscribe(object :
                SubscriberAdapter<String>() {
            override fun onNext(t: String) {
                super.onNext(t)
                preparePlayer(t)
            }
        })
    }

    private fun preparePlayer(videoUrl: String) {
        val videoUri = Uri.parse(videoUrl)
        // Measures bandwidth during playback. Can be null if not required.
        val bandwidthMeter = DefaultBandwidthMeter()
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "leisure"),
                bandwidthMeter)
        // Produces Extractor instances for parsing the media data.
        val extractorsFactory = DefaultExtractorsFactory()
        // This is the MediaSource representing the media to be played.
        val videoSource = ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null,
                null)
        // Prepare the player with the source.
        player?.prepare(videoSource)
        mPlayerView?.postDelayed({
            player?.playWhenReady = true
        }, 1000L)
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    private fun releasePlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }
}
