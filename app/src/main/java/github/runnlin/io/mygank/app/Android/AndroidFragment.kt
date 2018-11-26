package github.runnlin.io.mygank.app.Android

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import github.runnlin.io.mygank.MyApplication
import github.runnlin.io.mygank.app.base.BaseConfig
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.app.base.BaseFragment
import github.runnlin.io.mygank.http.GankResultBean
import github.runnlin.io.mygank.http.ResultsBean
import github.runnlin.io.mygank.http.RxGankService
import kotlinx.android.synthetic.main.fragment_android.*
import kotlinx.android.synthetic.main.fragment_android.view.*
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
class AndroidFragment : BaseFragment() {


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
        view.btn_android.setOnClickListener(this)

        androidAdapter = AndroidAdapter(dataList)
        rv_android.layoutManager = LinearLayoutManager(context)
        rv_android.adapter = androidAdapter
        androidAdapter.bindToRecyclerView(rv_android)
    }

    override fun onClick(v: View?) {
        v!!.btn_android.text = "Loading..."
        if (v == btn_android)
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
                view!!.btn_android.text = "Load error"
            }, {
                //onCompleted
                view!!.btn_android.text = "Load completed!"
                androidAdapter.notifyDataSetChanged()
            })
    }
}