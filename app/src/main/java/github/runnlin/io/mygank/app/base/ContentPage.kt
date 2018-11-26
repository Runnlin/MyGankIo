package github.runnlin.io.mygank.app.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.utils.ConstantUtils

/**
 * @author Runnlin
 * @date 2018/11/23/0023.
 */
abstract class ContentPage : FrameLayout {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initContentPage()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initContentPage()
    }

    constructor(context: Context) : super(context) {
        initContentPage()
    }

    private fun initContentPage() {
        showPage()
        loadDataAndRefreshPage()
    }

    //load state
    enum class PageState(val value: Int) {
        STATE_LOADING(0),
        STATE_SUCCESS(1),
        STATE_ERROR(2);
    }

    //default loading
    private var mState = PageState.STATE_LOADING
    private var loadingView: View? = null
    private var errorView: View? = null
    private var successView: View? = null

    /**
     * quest for data and refresh data
     */
    open fun loadDataAndRefreshPage() {
        //get data
        val data: Any = loadData()

        //check data's state and assign to mState
        mState = checkData(data)

        //refresh page
        showPage()
    }

    open fun refreshPage(o: Any?) {
        mState = if (o == null) {
            PageState.STATE_ERROR
        } else {
            PageState.STATE_SUCCESS
        }
        showPage()
    }

    /**
     * Check data and get which state
     */
    private fun checkData(data: Any?): PageState {
        return when (data) {
            null -> {
                PageState.STATE_ERROR
            }

            ConstantUtils.STATE_LOADING -> {
                PageState.STATE_LOADING
            }

            else -> {
                PageState.STATE_SUCCESS
            }
        }
    }

    /**
     * show page, control by state, hide others view
     */
    private fun showPage() {
        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        when (mState.value) {
            0 -> {
                if (loadingView == null) {
                    loadingView = View.inflate(context, R.layout.content_page_loading, null)
                }
                removeAllViews()
                addView(loadingView, params)
            }

            1 -> {
                removeAllViews()
//                successView = createSuccessView()
                addView(successView, params)
            }

            2 -> {
                if (errorView == null) {
                    errorView = View.inflate(context, R.layout.content_page_error, null)

                }
                removeAllViews()
                addView(errorView, params)
            }
        }
    }

    /**
     * every View's success view is different. let those make
     */
//    protected abstract fun createSuccessView():View

    /**
     * every View's loading data is different
     */
    protected abstract fun loadData():Any

}