package github.runnlin.io.mygank.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.view.ViewGroup
import android.graphics.drawable.Drawable
import android.view.View
import github.runnlin.io.mygank.MyApplication
import java.util.regex.Pattern


/**
 * @author Runnlin
 * @date 2018/11/23/0023.
 */
object CommonUtils {

    /**
     * 获取Resource对象
     */
    val resources: Resources
        get() = MyApplication.context!!.getResources()

    val isCameraCanUse: Boolean
        get() {
            var bool = false

            val pm = MyApplication.context!!.getPackageManager()
            bool = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                    pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)

            return bool
        }

    /**
     * dip转化成px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转化成dip
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * px转化成sp
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转化成px
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val scale = context.getResources().getDisplayMetrics().density
        return (spValue * scale + 0.5f).toInt()
    }

    /**
     * 在主线程执行任务
     */
    fun runOnUIThread(r: Runnable) {
//        MyApplication.getHandler().post(r)
    }

    /**
     * 获取Drawable资源
     */
    fun getDrawable(resId: Int): Drawable {
        return resources.getDrawable(resId)
    }

    /**
     * 获取字符串资源
     */
    fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    /**
     * 获取color资源
     */
    fun getColor(resId: Int): Int {
        return resources.getColor(resId)
    }

    /**
     * 获取dimens资源
     */
    fun getDimens(resId: Int): Float {
        return resources.getDimension(resId)
    }

    /**
     * 获取字符串数组资源
     */
    fun getStringArray(resId: Int): Array<String> {
        return resources.getStringArray(resId)
    }

    /**
     * 将自己从父容器中移除
     */
    fun removeSelfFromParent(child: View?) {
        // 获取父view
        if (child != null) {
            val parent = child!!.getParent()
            if (parent is ViewGroup) {
                val viewGroup = parent as ViewGroup
                // 将自己移除
                viewGroup.removeView(child)
            }
        }
    }

    /**
     * 验证车牌号码格式是否正确
     * @param plateNumber 车牌号码
     * @return ture正确 falise错误
     */
    fun checkPlateNumber(plateNumber: String): Boolean {
        val regex = "^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$"
        return Pattern.matches(regex, plateNumber)
    }


}