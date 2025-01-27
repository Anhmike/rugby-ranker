package com.ricknout.rugbyranker.matches.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getDimensionPixelSizeOrThrow
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.withTranslation
import androidx.core.text.inSpans
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.ricknout.rugbyranker.core.util.DateUtils
import com.ricknout.rugbyranker.matches.R
import com.ricknout.rugbyranker.matches.vo.WorldRugbyMatch

class WorldRugbyMatchDateItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    var matches: List<WorldRugbyMatch> = emptyList()
        set(value) {
            field = value
            matchHeaders = indexMatchHeaders().map {
                it.first to createHeader(it.second)
            }.toMap()
        }

    private var matchHeaders = indexMatchHeaders().map {
        it.first to createHeader(it.second)
    }.toMap()

    private lateinit var paint: TextPaint
    private var width: Int = 0
    private var paddingTop: Int = 0
    private var paddingStart: Int = 0
    private var paddingEnd: Int = 0
    private var datePaddingTop: Int = 0
    private var dayMonthTextSize: Int = 0
    private var dayYearTextSize: Int = 0

    private val today by lazy { context.getString(R.string.text_today) }

    init {
        context.withStyledAttributes(
                R.style.ItemDecoration_RugbyRanker_WorldRugbyMatch_Date,
                R.styleable.WorldRugbyMatchDateItemDecoration
        ) {
            paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                color = getColorOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_android_textColor)
                textSize = getDimensionOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_dayMonthTextSize)
                try {
                    typeface = ResourcesCompat.getFont(
                            context,
                            getResourceIdOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_android_fontFamily)
                    )
                } catch (_: Exception) {
                }
            }
            width = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_android_width)
            paddingTop = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_android_paddingTop)
            paddingStart = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_android_paddingStart)
            paddingEnd = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_android_paddingEnd)
            datePaddingTop = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_datePaddingTop)
            dayMonthTextSize = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_dayMonthTextSize)
            dayYearTextSize = getDimensionPixelSizeOrThrow(R.styleable.WorldRugbyMatchDateItemDecoration_dayYearTextSize)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (matchHeaders.isEmpty() || parent.isEmpty()) return

        var earliestFoundHeaderPos = -1
        var prevHeaderTop = Int.MAX_VALUE

        for (i in parent.childCount - 1 downTo 0) {
            val view = parent.getChildAt(i) ?: continue
            val viewTop = view.top + view.translationY.toInt()
            if (view.bottom > 0 && viewTop < parent.height) {
                val position = parent.getChildAdapterPosition(view)
                matchHeaders[position]?.let { layout ->
                    paint.alpha = (view.alpha * 255).toInt()
                    val left = paddingStart - width
                    val top = (viewTop + datePaddingTop)
                            .coerceAtLeast(datePaddingTop)
                            .coerceAtMost(prevHeaderTop - layout.height)
                    c.withTranslation(x = left.toFloat(), y = top.toFloat()) {
                        layout.draw(c)
                    }
                    earliestFoundHeaderPos = position
                    prevHeaderTop = viewTop
                }
            }
        }

        if (earliestFoundHeaderPos < 0) {
            earliestFoundHeaderPos = parent.getChildAdapterPosition(parent[0]) + 1
        }

        for (headerPos in matchHeaders.keys.reversed()) {
            if (headerPos < earliestFoundHeaderPos) {
                matchHeaders[headerPos]?.let {
                    val left = paddingStart - width
                    val top = (prevHeaderTop - it.height).coerceAtMost(datePaddingTop)
                    c.withTranslation(x = left.toFloat(), y = top.toFloat()) {
                        it.draw(c)
                    }
                }
                break
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(paddingStart, paddingTop, paddingEnd, 0)
    }

    private fun indexMatchHeaders() = matches
            .mapIndexed { index, match -> index to match.timeMillis }
            .distinctBy { DateUtils.getDayMonthYear(it.second) }

    private fun createHeader(millis: Long): StaticLayout {
        val isCurrentDay = DateUtils.isDayCurrentDay(millis)
        val text = SpannableStringBuilder().apply {
            inSpans(AbsoluteSizeSpan(dayMonthTextSize)) {
                val dayMonth = if (isCurrentDay) today else DateUtils.getDate(DateUtils.DATE_FORMAT_D_MMM, millis)
                append(dayMonth)
            }
            if (isCurrentDay) return@apply
            append(System.lineSeparator())
            inSpans(AbsoluteSizeSpan(dayYearTextSize)) {
                val dayYear = DateUtils.getDate(DateUtils.DATE_FORMAT_E_YYYY, millis)
                append(dayYear)
            }
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, paint, width)
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setIncludePad(false)
                    .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false)
        }
    }
}
