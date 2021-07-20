package com.example.myslideshow

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myslideshow.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private lateinit var player: MediaPlayer

    /**
     * MainActivity専用のクラスであるためネストする
     * fa: コンストラクタ引数にアクティビティが必要
     */
    class MyAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        private val resources = listOf(
            R.drawable.slide00, R.drawable.slide01,
            R.drawable.slide02, R.drawable.slide03,
            R.drawable.slide04, R.drawable.slide05,
            R.drawable.slide06, R.drawable.slide07,
            R.drawable.slide08, R.drawable.slide09
        )

        override fun getItemCount(): Int = resources.size

        /** ページが要求された時に呼ばれる
         *  引数でページ番号を受け取り、対応するフラグメントを戻り値として返す
         */
        override fun createFragment(position: Int): Fragment {
            Log.d("log：", "MyAdapter：createFragment()" + position)
            return ImageFragment.newInstance(resources[position])
        }
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2とFragmentStrateAdapterを関連付ける
        binding.pager.adapter = MyAdapter(this)
        Log.d("log：", "MainActivity：onCreate!!")

        // スクロール位置が変更される度にページに適用するPageTransformerを設定する
        binding.pager.setPageTransformer(ViewPager2PageTransformation())

        /**
         * 5秒で画像を切り替えるスライドショーを実装
         */
        val handler = Handler(Looper.getMainLooper())
        timer(period = 5000){
            handler.post {
                binding.apply {
                    pager.currentItem = (pager.currentItem + 1) % 10
                }
            }
        }
        /**
         * 画面表示中はバックサウンドを流す
         */
        player = MediaPlayer.create(this, R.raw.getdown)
        player.isLooping = true
    }

    override fun onResume() {
        super.onResume()
        player.start()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }
}

/**
 * ページ切り替えアニメ-ションに関するクラス
 */
class ViewPager2PageTransformation: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {

        when {
            // -1は画面の左側にはみ出て見えない状態
            position < -1 -> {
                // ビューの透過率
                page.alpha = 0.2f
                // ビューのX方向
                page.scaleX = 0.2f
                // ビュ-のY方向
                page.scaleY = 0.2f
            }
            // 画面上に表示されている時の処理
            // 0は画面画面中央に表示されている状態、1は画面の右側にはみ出て見えない状態
            position <= 1 -> {
                page.alpha = Math.max(0.2f, 1 - Math.abs (position))
                page.scaleX = Math.max(0.2f, 1 - Math.abs (position))
                page.scaleY = Math.max(0.2f, 1 - Math.abs (position))
            }
            else -> {
                // ビューの透過率
                page.alpha = 0.2f
                // ビューのX方向
                page.scaleX = 0.2f
                // ビュ-のY方向
                page.scaleY = 0.2f
            }
        }
    }


}