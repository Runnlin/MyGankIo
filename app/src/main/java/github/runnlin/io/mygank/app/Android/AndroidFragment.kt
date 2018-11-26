package github.runnlin.io.mygank.app.Android

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.app.base.BaseConfig
import github.runnlin.io.mygank.app.base.BaseFragment
import github.runnlin.io.mygank.http.GankResultBean
import github.runnlin.io.mygank.http.ResultsBean
import github.runnlin.io.mygank.http.RxGankService
import kotlinx.android.synthetic.main.fragment_android.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
class AndroidFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var retrofit: Retrofit
    private lateinit var rxGankService: RxGankService
    private lateinit var observable: Observable<GankResultBean>
    private lateinit var androidAdapter: AndroidAdapter
    private var dataList:ArrayList<ResultsBean> = ArrayList()
    private var pageNum = 1

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_android,
            container, false
        )
    }

    /**
     * Notice This method
     * This is the Created method
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
    }

    private fun initView(view: View) {
//        view.btn_android.setOnClickListener(this)
        swipeContainer = fragment_android
        swipeContainer.setOnRefreshListener(this)
        swipeContainer.setProgressViewOffset(true, 0, 100)
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        androidAdapter = AndroidAdapter(dataList)
        rv_android.layoutManager = LinearLayoutManager(context)
        rv_android.adapter = androidAdapter
        androidAdapter.bindToRecyclerView(rv_android)
    }

    override fun onClick(v: View?) {

    }

    override fun onRefresh() {
        clear()
        swipeContainer.isRefreshing = true
        requestData()
    }


    private fun initData() {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            //用来适配RxJava
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        rxGankService = retrofit.create(RxGankService::class.java)
        observable = rxGankService.getAndroidData(pageNum)
    }

    private fun clear() {
        dataList.clear()
        androidAdapter.notifyDataSetChanged()
    }

    override fun requestData(): Any {
        return observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.results
            }
            .switchMap {
                return@switchMap Observable.from(it)
            }
            .subscribe({
                //onNext
                dataList.add(it)
                Log.e("zrl", "add: "+it.desc)
            }, {
                //onError
                it.printStackTrace()
                swipeContainer.isRefreshing = false
            }, {
                //onCompleted
                androidAdapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            })
    }
}