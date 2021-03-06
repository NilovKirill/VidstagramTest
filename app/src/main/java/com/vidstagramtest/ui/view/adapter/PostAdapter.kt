package com.vidstagramtest.ui.view.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
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
            Glide.with(context).clear(holder.mediaImage)
            Glide.with(context).clear(holder.userAvatar)
            Glide
                .with(context)
                .load(R.drawable.default_avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.userAvatar)

            currentItem.videoUrl?.let { videoUrl ->
                //Load video frame
                val options = RequestOptions().frame(999999)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(context)
                    .asBitmap()
                    .apply(options)
                    .load(videoUrl)
                    .into(holder.mediaImage)
                holder.mediaImage.visibility = View.VISIBLE
                holder.playerView.visibility = View.GONE
                holder.mediaImage.setOnClickListener {
                    holder.mediaImage.visibility = View.GONE
                    holder.playerView.visibility = View.VISIBLE
                    try {
                        val video: Uri =
                            Uri.parse(videoUrl)
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
                        //Todo refactor it. Should have only one instance of exoplayer
                        val exoPlayer: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context)
                        holder.playerView.player = exoPlayer
                        exoPlayer.prepare(mediaSource)
                        exoPlayer.playWhenReady = false
                    } catch (e: Exception) {

                    }
                }
            }
        }


        holder.postTitle.text = currentItem.title ?: ""
        holder.userName.text = currentItem.userName ?: ""
        currentItem.timestamp?.let {
            holder.timeTv.text = getFormattedDate(it)
        }
    }

    override fun getItemCount() = dataSet.size

    fun setList(items: List<PostModel>) {
        dataSet.clear()
        dataSet.addAll(items)
        dataSet.sortByDescending { it.timestamp }
        notifyDataSetChanged()
    }

    private fun getFormattedDate(
        unixTime: Long
    ): String? {
        val simpleDateFormat = SimpleDateFormat("dd MMM, h:mm a", Locale.ENGLISH)
        return simpleDateFormat.format(unixTime)
    }

    fun addNewItems(newPosts: List<PostModel>) {
        dataSet.addAll(0, newPosts)
        notifyItemRangeInserted(0, newPosts.size)
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