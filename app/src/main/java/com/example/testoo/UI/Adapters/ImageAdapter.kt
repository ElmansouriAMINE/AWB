import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.testoo.Domain.models.ImageItem
import com.example.testoo.R

class ImageAdapter : ListAdapter<ImageItem, ImageAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)

        var textDateExipration: TextView
        var textNumeroCarte: TextView
        var userName : TextView

        init {
            textDateExipration = itemView.findViewById(R.id.textDateExpiration)
            textNumeroCarte = itemView.findViewById(R.id.textNumeroCarte)
            userName = itemView.findViewById(R.id.textUserName)

        }

        fun bindData(item: ImageItem) {
//            Glide.with(itemView)
//                .load(item.url)
//                .into(imageView)
            val drawableResourceId = itemView.context.resources.getIdentifier(
                item.imageUrl,
                "drawable", itemView.context.packageName
            )
            Glide.with(itemView)
                .load(drawableResourceId)
                .transform(GranularRoundedCorners(30F, 30F, 0f, 0F))
                .into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)

        holder.textNumeroCarte.setText(imageItem.numeroCarte)
        holder.textDateExipration.setText(imageItem.dateExpiration)
        holder.userName.setText(imageItem.userName)
    }
}
