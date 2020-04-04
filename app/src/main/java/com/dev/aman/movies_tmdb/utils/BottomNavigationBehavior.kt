package com.dev.aman.movies_tmdb.utils

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import java.lang.Float.min
import kotlin.math.max

class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))
    }
}