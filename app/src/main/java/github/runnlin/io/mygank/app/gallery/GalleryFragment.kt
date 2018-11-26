package github.runnlin.io.mygank.app.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.app.base.BaseConfig
import github.runnlin.io.mygank.http.GankResultBean
import github.runnlin.io.mygank.http.ResultsBean
import github.runnlin.io.mygank.http.RxGankService
import github.runnlin.io.mygank.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_gallery.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author Runnlin
 * @date 2018/11/22/0022.
 */
class GalleryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    private lateinit var retrofit: Retrofit
    private lateinit var rxGankService: RxGankService
    private lateinit var observable: Observable<GankResultBean>

    private var imagesUrlList:ArrayList<ResultsBean> = ArrayList()
    private lateinit var imageAdapter: ImageViewAdapter
    private lateinit var mySwipeRefreshLayout:SwipeRefreshLayout
    private var page = 1

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(
            R.layout.fragment_gallery,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
    }

    private fun initData() {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//用来适配RxJava
            .build()
    }

    private fun initView() {
        imageAdapter = ImageViewAdapter(imagesUrlList)
        recycler_view.adapter = imageAdapter
        imageAdapter.bindToRecyclerView(recycler_view)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(
            3, StaggeredGridLayoutManager.VERTICAL
        )
        recycler_view.animation = null

        mySwipeRefreshLayout = fragment_gallery
        mySwipeRefreshLayout.setOnRefreshListener(this)
        //set draw down location
        mySwipeRefreshLayout.setProgressViewOffset(true, 0, 100)
        //set cycle size
        mySwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)
        //set cycle color
        mySwipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    override fun onRefresh() {
        imagesUrlList.clear()
        imageAdapter.notifyDataSetChanged()
        mySwipeRefreshLayout.isRefreshing = true
        loadData(page)
    }

    private fun loadData(_page: Int) {
        rxGankService = retrofit.create(RxGankService::class.java)
        observable = rxGankService.getFuliData(_page)
        observable
            .timeout(20, TimeUnit.SECONDS)
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
                imagesUrlList.add(it)
                Log.d("zrl", it.toString())
            }, {
                //onError
                it.printStackTrace()
                ToastUtils.showShort(activity!!.applicationContext, "Loading error")
                mySwipeRefreshLayout.isRefreshing = false
            }, {
                //onCompleted
                imageAdapter.notifyDataSetChanged()
                mySwipeRefreshLayout.isRefreshing = false
            })
    }

    private fun loadNext() {
        //TODO: 下拉加载
        if (page < 20)
            loadData(++page)
    }
}