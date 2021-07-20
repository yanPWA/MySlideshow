package com.example.myslideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myslideshow.databinding.FragmentImageBinding

private const val IMG_RES_ID = "IMG_RES_ID"

class ImageFragment : Fragment() {

    private var imageResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResId = it.getInt(IMG_RES_ID)
        }
        Log.d("log：", "ImageFragment：onCreate()!!")
    }

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding = FragmentImageBinding.inflate(inflater, container , false)
            Log.d("log：", "ImageFragment：onCreateView()!!")
            return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("log：", "ImageFragment：onDestroy()!!")
    }

    /**
     * companion object宣言をしておくと、そのオブジェクトのメソッドは
     * ImageFragmentクラスのインスタンスが生成される前から使用可能となる
     * Javaでいうstaticメソッド
     */
    companion object {
        fun newInstance(imageResId: Int) =
            // このフラグメントのインスタンスを生成
            ImageFragment().apply {
                // Bundleクラスのインスタンスを生成
                arguments = Bundle().apply {
                    // 画像リソースIDをBundleクラスに格納
                    putInt(IMG_RES_ID, imageResId)
                }
            }
    }

    // Bundleに保存された情報にしたがってイメージ画像を画面に表示させる
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageResId?.let {
            binding.imageView.setImageResource(it)
            Log.d("log：" , "ImageFragment：onActivityCreated()!!" + imageResId)
        }
    }
}