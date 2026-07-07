package com.xiaomi.subscreencenter

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import java.util.ArrayList

class MainPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "MainPanel"
    }

    private var widgetsList: List<Any> = ArrayList()
    
    private val activeSlots = ArrayList<SubScreenSlotMock>(3)
    
    private var currentIndex = 0
    private var isResumed = false
    private var isInAodMode = false

    init {
        Log.d(TAG, "Subscreen MainPanel layout view created.")
        setupLayoutStructure()
    }

    private fun setupLayoutStructure() {
        for (i in 0..2) {
            activeSlots.add(SubScreenSlotMock(i))
        }
    }

    fun updateActiveWidgetDisplay(forceRefresh: Boolean) {
        if (widgetsList.isEmpty()) {
            Log.w(TAG, "No elements found to project on the sub-screen.")
            return
        }

        Log.d(TAG, "Refreshing back display viewport logic. Active index: $currentIndex")
        
        val centerSlot = activeSlots[1]
        
        bindTargetViewToHardwareSlot(currentIndex, centerSlot)
    }

    private fun bindTargetViewToHardwareSlot(widgetIndex: Int, slot: SubScreenSlotMock) {
        Log.d(TAG, "Binding panel index [$widgetIndex] to hardware display container block [${slot.slotId}]")
    }

    fun onResume() {
        isResumed = true
        Log.d(TAG, "Back display screen viewport awake.")
        updateActiveWidgetDisplay(forceRefresh = false)
    }

    fun onPause() {
        isResumed = false
        Log.d(TAG, "Back display screen viewport asleep.")
    }

    fun onAodStateChanged(inAod: Boolean) {
        this.isInAodMode = inAod
        Log.d(TAG, "AOD power state change captured: $inAod")
    }
}

// mock slots to replicated function on C0568o
data class SubScreenSlotMock(val slotId: Int, var currentAttachedView: View? = null)