package github.runnlin.io.mygank.utils

/**
 * @author Runnlin
 * @date 2018/11/23/0023.
 */
interface ConstantUtils {
    companion object {
        /**
         * 加载界面的三种状态
         */
        val STATE_LOADING = "zs"
        val STATE_SUCCESSED = "shanyao"
        val STATE_FAILED: String? = null
        /**
         * 根据资源id跳转的界面
         */
        val FIND_CARPORT_LIST = 1// 找车场
        val PARKING_DETAIL = 2// 找车场列表的详情
        val CARPORT_DETAIL = 3// 车位的详情
        val NAVI_FRAGMENT = 4// 导航界面
        val SEARCH_MAP = 5// 地图搜索界面

        /**
         * 界面中的一些常亮
         */
        val MAX_ITEM_LOAD_MORE = 5// 当首次请求数据超过条后开启加载更多功能
        val PAGER_ROWS = 7// 每一页的数据
        val EXIT_APP = "exit_app"// 退出登陆
    }
}