package com.example.myapplication

import android.app.Application
import android.content.Context
import android.gesture.GestureStroke
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue

class ScreenUtil {
    companion object{
        //返回dp
        fun getScreenWidth(context: Context):Int{
            /*val px = context.resources.displayMetrics.widthPixels
            return px2dp(context,px.toFloat())*/
            return context.resources.displayMetrics.widthPixels
        }

        // dp转px
        fun dp2px(context: Context, dp:Float):Int{
            val scale : Float = context.resources.displayMetrics.density;
            return (dp * scale ).toInt()
        }

        //px转dp
        fun px2dp(context: Context, px:Float):Int{
            val scale : Float = context.resources.displayMetrics.density;
            return (px / scale ).toInt()
        }


        /**
         * @param color 为资源id，
         * @param radius 为圆角大小
         * @return GradientDrawable
         * @author hy
         */
        fun genShapeDrawable(context: Context,color:Int,radius:Int =0) : GradientDrawable{
            val gd = GradientDrawable()
            gd.setColor(context.resources.getColor(color,null))
            gd.cornerRadius = radius.toFloat()
            return gd
        }
    }
}