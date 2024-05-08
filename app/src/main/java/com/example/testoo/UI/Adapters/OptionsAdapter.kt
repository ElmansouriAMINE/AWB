package com.example.testoo.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testoo.R


class OptionsAdapter(private val options: List<String>) : RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    private var listener: OnOptionClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.option_item, parent, false)
        return OptionViewHolder(view)
    }

    interface OnOptionClickListener {
        fun onOptionClicked(option: String)
    }

    fun setOnOptionClickListener(listener: OnOptionClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val optionTextView: TextView = itemView.findViewById(R.id.optionTextView)

        fun bind(option: String) {
            optionTextView.text = option
            optionTextView.setOnClickListener {
                listener?.onOptionClicked(option)
            }
        }
    }
}
