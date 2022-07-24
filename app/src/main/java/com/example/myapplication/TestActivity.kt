package com.example.myapplication


import android.content.Context

import android.graphics.drawable.GradientDrawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.RecyclerView

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


class TestActivity : AppCompatActivity() {
    companion object{
        val sideWith = 40//dp
    }
    private val vp : ViewPager2 by lazy { findViewById(R.id.vp) }
    private val tv :TextView by lazy { findViewById(R.id.main_tv) }
    private val tv2 :TextView by lazy { findViewById(R.id.main_tv2) }
    private val tv3 :TextView by lazy { findViewById(R.id.main_tv3) }
    private  lateinit var vm :MainViewModule
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registeViewModel()
        fatchData()
        initView()
    }

    private fun registeViewModel(){
        vm = ViewModelProvider(this)[MainViewModule::class.java]
        vm.colors.observe(this){}
    }

    private fun fatchData(){
        vm.fetchBg()
    }

    private  fun initView(){
        vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vp.adapter = object :FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                Log.d("AA","adapter getIntemcount:${ vm.colors.value?.size?:0}")
                return  vm.colors.value?.size?:0
            }

            override fun createFragment(position: Int): Fragment {
                Log.d("AA","adapter createFragment")
                /*return object :Fragment(){
                    override fun onCreateView(
                        inflater: LayoutInflater,
                        container: ViewGroup?,
                        savedInstanceState: Bundle?
                    ): View? {
                        val view = inflater.inflate(R.layout.fragment_layout,container,false)
                        val gd = GradientDrawable()
                        gd.setColor(vm.colors.value?.get(position) ?:0x000000)
                        view.background = gd
                        return view
                    }
                }*/
                return MFragment(resources.getColor(vm.colors.value?.get(position) ?:0x000000,null))
            }
        }
        vp.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tv.text = "现在是第${position}页"
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val str="滚动时 position= $position,positionOffset = $positionOffset\n,positionOffsetPixels=$positionOffsetPixels"
                tv2.text = str
            }
        })
        vp.setPageTransformer(Ptransformer(tv3))
        vp.currentItem = 2
        vp.offscreenPageLimit = 3 //设置缓存的页面数， 当一屏显示多个page需要更改
        val rv = vp.getChildAt(0) as RecyclerView
        val padding =  ScreenUtil.dp2px(this,30.toFloat())
        rv.setPadding(padding,0,padding,0)
        rv.addItemDecoration(SpaceItemDecoratioen(10))
        rv.clipToPadding =false
        Log.d("AA","initView over")
    }

    override fun onResume() {
        super.onResume()
    }

    class Ptransformer(val tv3:TextView) : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            tv3.text = "transformPage,position =$position"
        }

    }

    class MviewPageAdapter(val context: Context,val vm: MainViewModule):RecyclerView.Adapter<MviewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MviewHolder {
            return MviewHolder(LayoutInflater.from(context).inflate(R.layout.pagel_layout,parent,false))
        }

        override fun onBindViewHolder(holder: MviewHolder, position: Int) {
            holder.update(position, vm.colors.value?.get(position) ?:0 )
        }

        override fun getItemCount(): Int {
            return vm.colors.value?.size ?: 0
        }

    }

    class MviewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        fun update(position: Int,color:Int){
            val gb =GradientDrawable()
            gb.setColor(color)
            itemView.background = gb
        }
    }
}