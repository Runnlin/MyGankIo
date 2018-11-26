package github.runnlin.io.mygank.app.gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.http.ResultsBean
import java.util.ArrayList

/**
 * @author Runnlin
 * @date 2018/11/22/0022.
 */
class ImageViewAdapter(data: MutableList<ResultsBean>?) :
    BaseQuickAdapter<ResultsBean, BaseViewHolder>(
        R.layout.item_image, data
    ), View.OnClickListener {

    override fun convert(helper: BaseViewHolder?, item: ResultsBean) {
        val options = RequestOptions()
            .centerInside()

        Glide
            .with(mContext)
            .load(item.url)
            .apply(options)
            .into(helper!!.getView(R.id.show_img))
    }

    override fun onClick(v: View?) {

    }

}