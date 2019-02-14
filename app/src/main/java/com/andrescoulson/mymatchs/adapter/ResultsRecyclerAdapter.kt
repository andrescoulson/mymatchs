package com.andrescoulson.mymatchs.adapter

import android.databinding.DataBindingUtil
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.andrescoulson.mymatchs.R
import com.andrescoulson.mymatchs.config.Util
import com.andrescoulson.mymatchs.databinding.ItemFixtureBinding
import com.andrescoulson.mymatchs.model.Data
import java.lang.StringBuilder
import java.util.*

class ResultsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var lisdata = arrayListOf<Data>()
    var skeleton = true
    fun setData(list: ArrayList<Data>) {
        this.lisdata = list
        skeleton=false
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (skeleton) 0 else if (lisdata[position].match == null) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (skeleton) {
            (holder as ViewHolder).binding.skeletonGroup.startAnimation()
        } else {
            if (lisdata[position].match != null){
                (holder as ViewHolder).binding.skeletonGroup.finishAnimation()
                holder.bind(lisdata[position])
            }
            else
                (holder as SectionViewHolder).bind(lisdata[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0)
            return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_fixture,
                    parent,
                    false
                )
            )
        else
            return SectionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false))
    }

    override fun getItemCount(): Int {
        return if (skeleton) 4 else lisdata.size
    }


    class ViewHolder(itemView: com.andrescoulson.mymatchs.databinding.ItemFixtureBinding?) :
        RecyclerView.ViewHolder(itemView?.root) {
        val binding = itemView!!

        fun bind(data: Data) {

            if (data.match != null) {
                binding.txtTitleCompetiton.text = data.match?.competitionStage?.competition?.name
                val stringBuilder = StringBuilder()
                stringBuilder.append(data.match?.venue?.name)
                    .append(" | ")
                binding.txtDatePlace.text = stringBuilder
                binding.txtDate.text = Util.getDates(data.match?.date!!)

                binding.txtHomeTeam.text = data.match?.homeTeam?.name
                binding.txtAwayTeam.text = data.match?.awayTeam?.name
                val calendar = Calendar.getInstance()
                calendar.time = Util.getDate(data.match?.date!!)
                binding.txtScoreHome.text = data.match?.score?.home?.toString()
                binding.txtScoreAway.text = data.match?.score?.away?.toString()
                if (data.match?.score?.winner != null) {
                    if (data.match?.score?.winner?.equals("home")!!) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            binding.txtScoreHome.setTextColor(binding.txtScoreHome.context.getColor(android.R.color.holo_blue_bright))
                        }
                    }
                    if (data.match?.score?.winner?.equals("away")!!) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            binding.txtScoreAway.setTextColor(binding.txtScoreAway.context.getColor(android.R.color.holo_blue_bright))
                        }
                    }
                }
            }

        }

    }

    class SectionViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title = itemView?.findViewById<TextView>(R.id.txt_section)
        fun bind(data: Data) {
            title?.text = data.name
        }
    }

}