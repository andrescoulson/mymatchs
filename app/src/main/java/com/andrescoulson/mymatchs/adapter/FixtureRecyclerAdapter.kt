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
import com.andrescoulson.mymatchs.databinding.ItemResultsBinding
import com.andrescoulson.mymatchs.model.Data
import java.lang.StringBuilder
import java.util.*

class FixtureRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var lisdata = arrayListOf<Data>()
    var skeleton = true
    fun setData(list: ArrayList<Data>) {
        this.lisdata = list
        skeleton = false
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (skeleton) {
            (holder as ViewHolder).binding.skeletonGroup.startAnimation()
        } else {
            if (lisdata[position].match != null) {
                (holder as ViewHolder).binding.skeletonGroup.finishAnimation()
                holder.bind(lisdata[position])
            } else
                (holder as SectionViewHolder).bind(lisdata[position])
        }

    }


    override fun getItemViewType(position: Int): Int {

        return if (skeleton) 0 else if (lisdata[position].match == null) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_results, parent, false
                )
            )
        else
            SectionViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_section,
                    parent,
                    false
                )
            )
    }

    override fun getItemCount(): Int {
        return if (skeleton) 4 else lisdata.size
    }


    class ViewHolder(itemView: com.andrescoulson.mymatchs.databinding.ItemResultsBinding?) :
        RecyclerView.ViewHolder(itemView?.root) {
        var binding: ItemResultsBinding = itemView!!

        fun bind(data: Data) {

            if (data?.match != null) {
                binding.txtTitleCompetiton.text = data.match?.competitionStage?.competition?.name
                val stringBuilder = StringBuilder()
                stringBuilder.append(data.match?.venue?.name)
                    .append(" | ")
                binding.txtDatePlace.text = stringBuilder
                binding.txtDate.text = Util.getDates(data.match?.date!!)
                if (data.match?.state != null && !data.match?.state?.equals("preMatch")!!) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtDate.setTextColor(binding.txtDate.context.getColor(android.R.color.holo_red_light))
                        binding.txtState.visibility = View.VISIBLE
                        binding.txtState.text = data.match?.state
                    }
                }
                binding.txtHomeTeam.text = data.match?.homeTeam?.name
                binding.txtAwayTeam.text = data.match?.awayTeam?.name
                val calendar = Calendar.getInstance()
                calendar.time = Util.getDate(data.match?.date!!)
                binding.txtDay.text = calendar.get(Calendar.DAY_OF_WEEK).toString()
                binding.txtNameDay.text = Util.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))
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