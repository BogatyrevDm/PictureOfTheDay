package com.example.pictureoftheday.view.lop

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureoftheday.databinding.FragmentLopItemViewBinding
import com.example.pictureoftheday.model.lop.Planet

class RecyclerViewLOPAdapter(private val dragListener: OnStartDragListener) :
    RecyclerView.Adapter<RecyclerViewLOPAdapter.LOPViewHolder>(), ItemTouchHelperAdapter {
    private var dataSource: MutableList<Pair<Planet, Boolean>> = mutableListOf()

    inner class LOPViewHolder(val binding: FragmentLopItemViewBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {
        fun onBind(data: Pair<Planet, Boolean>) {
            binding.moveItemUp.setOnClickListener { moveUp() }
            binding.moveItemDown.setOnClickListener { moveDown() }
            binding.dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
            binding.description.visibility = if (data.second) View.VISIBLE else View.GONE
            binding.title.setOnClickListener { toggleText() }
            binding.title.text = data.first.title
            binding.description.text = data.first.description
        }

        private fun toggleText() {
            dataSource[layoutPosition] = dataSource[layoutPosition].let { it.first to !it.second }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                dataSource.removeAt(currentPosition).apply {
                    dataSource.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < dataSource.size - 1 }?.also { currentPosition ->
                dataSource.removeAt(currentPosition).apply {
                    dataSource.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    fun setDataSource(dataSourceToSet: MutableList<Pair<Planet, Boolean>>) {
        dataSource = dataSourceToSet

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LOPViewHolder {
        val binding =
            FragmentLopItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LOPViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LOPViewHolder, position: Int) {
        holder.onBind(dataSource[position])
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataSource.removeAt(fromPosition).apply {
            dataSource.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}
