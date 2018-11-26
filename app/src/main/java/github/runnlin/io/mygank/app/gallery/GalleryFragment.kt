package github.runnlin.io.mygank.app.gallery

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import github.runnlin.io.mygank.MyApplication
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.R.id.recycler_view
import github.runnlin.io.mygank.R.id.show_img
import github.runnlin.io.mygank.app.Android.AndroidAdapter
import github.runnlin.io.mygank.app.base.BaseConfig
import github.runnlin.io.mygank.http.GankResultBean
import github.runnlin.io.mygank.http.ResultsBean
import github.runnlin.io.mygank.http.RxGankService
import github.runnlin.io.mygank.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_android.*
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
class GalleryFragment : Fragment() {

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

        mySwipeRefreshLayout = SwipeRefreshLayout(activity!!.applicationContext)
        return inflater.inflate(
            R.layout.fragment_gallery,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()

        loadData()
//        loadNext()
    }

    private fun initData() {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//用来适配RxJava
            .build()
        //set draw down location
        mySwipeRefreshLayout.setProgressViewOffset(true, 50, 200)

        //set cycle size
        mySwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)

        //set cycle color
        mySwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright)

        //set background
        mySwipeRefreshLayout.setProgressBackgroundColorSchemeResource(
            android.R.color.holo_red_dark
        )
    }

    private fun initView() {
        imageAdapter = ImageViewAdapter(imagesUrlList)
        recycler_view.adapter = imageAdapter
        imageAdapter.bindToRecyclerView(recycler_view)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.animation = null

        mySwipeRefreshLayout.setOnRefreshListener{
            imagesUrlList.clear()
            imageAdapter.notifyDataSetChanged()
            Toast.makeText(activity!!.applicationContext, "Reloading..", Toast.LENGTH_LONG).show()

            loadData()
        }
    }

    private fun loadData() {
        rxGankService = retrofit.create(RxGankService::class.java)
        observable = rxGankService.getFuliData(page)
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
            }, {
                //onCompleted
                ToastUtils.showShort(activity!!.applicationContext, "Loading completed")
                imageAdapter.notifyDataSetChanged()
            })
    }

    private fun loadNext() {
        loadData()
    }
}