package com.carlyu.xywlogin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carlyu.xywlogin.R
import com.carlyu.xywlogin.settings.SettingsItems
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsRecyclerAdapter(private val dataSet: Array<SettingsItems>) :
    RecyclerView.Adapter<SettingsRecyclerAdapter.MyViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*      val textViewNumber: TextView
                val textViewContent: TextView

                init {
                    // Define click listener for the ViewHolder's View.
                    textViewNumber = view.findViewById(R.id.item_num)
                    textViewContent = view.findViewById(R.id.item_content)
                }*/
        val textViewNumber: TextView = view.findViewById(R.id.item_num)
        val textViewContent: TextView = view.findViewById(R.id.item_content)
        val switchMaterial: SwitchMaterial = view.findViewById(R.id.item_switch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsRecyclerAdapter.MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_settings_recycler_view_items, parent, false)

        return MyViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.apply {
            textViewNumber.text = (position + 1).toString()
            textViewContent.text = dataSet[position].SettingsItemName
            switchMaterial.visibility =
                if (dataSet[position].ItemHasSwitch)
                    View.VISIBLE
                else
                    View.GONE

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
