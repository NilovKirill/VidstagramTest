package com.vidstagramtest.ui.view.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.vidstagramtest.R
import com.vidstagramtest.model.PostModel
import kotlinx.android.synthetic.main.post_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class PostAdapter(
    private val context: Context?
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var dataSet: ArrayList<PostModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.post_item,
            parent, false
        )
        return PostViewHolder(itemView)
    }

    override fun onViewRecycled(holder: PostViewHolder) {
        holder.playerView.player?.stop()
        holder.playerView.player?.release()
        holder.playerView.player = null
        super.onViewRecycled(holder)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = dataSet[position]
        context?.let { context ->
            Glide
                .with(context)
                .load(R.drawable.default_avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.userAvatar)


            currentItem.videoUrl?.let {
                holder.mediaImage.setOnClickListener {
                    var exoPlayer: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context)
                    try {
                        val bandwidthMeter: BandwidthMeter =
                            DefaultBandwidthMeter.Builder(context).build()
                        val trackSelector: TrackSelector =
                            DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
                        exoPlayer = ExoPlayerFactory.newSimpleInstance(context) as SimpleExoPlayer
                        val video: Uri =
                            Uri.parse(currentItem.videoUrl)
                        val dataSourceFactory =
                            DefaultHttpDataSourceFactory("video")
                        val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
                        val mediaSource: MediaSource = ExtractorMediaSource(
                            video,
                            dataSourceFactory,
                            extractorsFactory,
                            null,
                            null
                        )
                        holder.playerView.player = exoPlayer
                        exoPlayer.prepare(mediaSource)
                        exoPlayer.playWhenReady = false
                    } catch (e: Exception) {
                        Log.e("ViewHolder2", "exoplayer error$e")
                    }
                }
            }
        }

        currentItem.title?.let {
            holder.postTitle.text = it
        }
        currentItem.userName?.let {
            holder.userName.text = it
        }
        currentItem.timestamp?.let {
            holder.timeTv.text = getFormattedDate(it)
        }
    }

    override fun getItemCount() = dataSet.size

    fun setList(items: List<PostModel>) {
        dataSet.clear()
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    private fun getFormattedDate(
        unixTime: Long
    ): String? {
        val simpleDateFormat: SimpleDateFormat =
            SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.ENGLISH)
        return simpleDateFormat.format(unixTime)
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postTitle: TextView = itemView.postTitle
        val userAvatar: ImageView = itemView.userAvatar
        val userName: TextView = itemView.userName
        val timeTv: TextView = itemView.timestamp
        val playerView: PlayerView = itemView.playerView
        val mediaImage: ImageView = itemView.mediaImage
    }
}