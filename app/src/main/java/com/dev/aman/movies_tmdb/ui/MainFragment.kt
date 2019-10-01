package com.dev.aman.movies_tmdb.ui

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dev.aman.movies_tmdb.BaseFragment
import com.dev.aman.movies_tmdb.R
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : BaseFragment() {

    private lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_main, container, false)

        setScrollViewListner()

        return root
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun setScrollViewListner() {
        root.fragment_main_scrollview.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if(scrollY - oldScrollY > 0){
                Log.d(TAG, "Scroll DOWN")
                (activity as AppCompatActivity).supportActionBar!!.hide()
            }else {
                Log.d(TAG, "Scroll UP")
                (activity as AppCompatActivity).supportActionBar!!.show()
            }
        }
    }

    companion object{
        private var TAG = MainFragment::class.java.simpleName

        fun mainFragmentInstance() : MainFragment {
            return MainFragment()
        }
    }
}