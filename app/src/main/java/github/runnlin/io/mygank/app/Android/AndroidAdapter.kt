package github.runnlin.io.mygank.app.Android

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.http.ResultsBean

/**
 * @author Runnlin
 * @date 2018/11/23/0023.
 */
class AndroidAdapter(data: MutableList<ResultsBean>?) :
    BaseQuickAdapter<ResultsBean, BaseViewHolder>(
        R.layout.item_android, data) {

    override fun convert(helper: BaseViewHolder?, item: ResultsBean?) {
        if  (item!!.desc != null && item.images[0] != null) {
            helper!!.setText(R.id.tv_android, item.desc)
            Glide.with(mContext)
                .load(item.images[0])
                .into(helper.getView(R.id.iv_android))
        }
    }
}