package github.runnlin.io.mygank.app.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.runnlin.io.mygank.utils.CommonUtils
import rx.Subscriber
import kotlin.collections.ArrayList

/**
 * @author Runnlin
 * @date 2018/11/23/0023.
 */
abstract class BaseFragment:Fragment(), View.OnClickListener {

    open var contentPage: ContentPage? = null
    open var pdLoading:ProgressDialog? = null
    private var subscribers: ArrayList<Subscriber<*>>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        pdLoading = ProgressDialog(activity)
        pdLoading!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pdLoading!!.setMessage("Hold on...")
        pdLoading!!.setCanceledOnTouchOutside(false)
        pdLoading!!.setCancelable(true)

//        subscribers = ArrayList()
        if (contentPage == null) {
            contentPage = object : ContentPage(activity!!.applicationContext) {
//                override fun createSuccessView(): View {
//                    return getSuccessView()
//                }

                override fun loadData(): Any {
                    return requestData()
                }
            }
        } else {
            CommonUtils.removeSelfFromParent(contentPage)
        }
        return contentPage
    }

    /**
     * return fragment's view
     */
//    protected abstract fun getSuccessView():View

    protected abstract fun requestData(): Any

    open fun refreshPage(o:Any) {
        contentPage!!.refreshPage(o)
    }

    override fun onDestroy() {
        super.onDestroy()
//        for (subscriber in subscribers!!) {
//            if (!subscriber.isUnsubscribed)
//                subscriber.unsubscribe()
//        }
    }

    open fun <T> addSubscriber(subscriber: Subscriber<T>):Subscriber<T> {
        subscribers!!.add(subscriber)
        return subscriber
    }
}