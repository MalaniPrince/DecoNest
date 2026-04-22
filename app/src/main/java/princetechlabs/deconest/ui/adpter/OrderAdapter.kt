package princetechlabs.deconest.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemOrderBinding
import princetechlabs.deconest.ui.data.OrderData

class OrderAdapter(
    private val orders: List<OrderData>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.tvOrderIdItem.text = "Order #${order.orderId}"
        holder.binding.tvOrderStatus.text = order.status
        val itemCount = order.items.sumOf { it.quantity }
        holder.binding.tvOrderItems.text = "$itemCount item${if (itemCount != 1) "s" else ""}"
        holder.binding.tvOrderAddress.text = order.address
        holder.binding.tvOrderTotal.text = "₹${String.format("%,d", order.totalAmount)}"
        
        holder.itemView.setOnClickListener {
            val intent = android.content.Intent(holder.itemView.context, princetechlabs.deconest.ui.home.OrderDetailActivity::class.java)
            intent.putExtra("order_id", order.orderId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orders.size
}
