package princetechlabs.deconest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import princetechlabs.deconest.databinding.ItemCouponBinding

class CouponAdapter(
    private val coupons: List<CouponData>,
    private val onCopy: (String) -> Unit
) : RecyclerView.Adapter<CouponAdapter.CouponViewHolder>() {

    inner class CouponViewHolder(val binding: ItemCouponBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val binding = ItemCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CouponViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        val coupon = coupons[position]
        holder.binding.tvCouponCode.text = coupon.code
        holder.binding.tvCouponDiscount.text = "${coupon.discountPercent}% OFF"
        holder.binding.tvCouponExpiry.text = "Valid till: ${coupon.expiryDate}"
        holder.binding.tvCouponDescription.text = coupon.description
        holder.binding.btnCopyCoupon.setOnClickListener { onCopy(coupon.code) }
    }

    override fun getItemCount(): Int = coupons.size
}
