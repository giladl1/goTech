package com.levins.junky
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class RecyclerAdapter2(context: Context, private var result: List<numberWithData>):
            RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder>() {
        private val context: Context =context
        class MyViewHolder(val v: View, val context: Context) : RecyclerView.ViewHolder(v) {
            val textViewDescription: TextView

            init {
                // Define click listener for the ViewHolder's View.
                textViewDescription=v.textViewDescription
            }
        }
        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): RecyclerAdapter2.MyViewHolder {
            val textView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item , parent, false)
            return MyViewHolder(textView,parent.context)//,myDatasetIds
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textViewDescription.text = result!![position].number.toString()
            if(result!![position].hasSibling) {
                holder.textViewDescription.height = 150
                holder.textViewDescription.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
            }
            else {
                holder.textViewDescription.height = 100
                holder.textViewDescription.setBackgroundColor(ContextCompat.getColor(context,R.color.orange))
            }

        }
    //    // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = result!!.size //size()

    }





