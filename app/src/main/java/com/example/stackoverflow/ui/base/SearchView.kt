package com.example.stackoverflow.ui.base

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.example.stackoverflow.R
import com.example.stackoverflow.utils.dpToPx
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchView(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    companion object {
        private const val MIN_TEXT_LENGTH = 4
    }

    private var debounceTime = 0
    private var minimumQueryTextLength = 0
    private var mQueryListener: QueryListener? = null
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val subject = PublishSubject.create<String>()

    private val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.trim().isNotEmpty()) {
                this@SearchView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_clear,
                    0
                )
            } else {
                this@SearchView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_search_grey,
                    0,
                    0,
                    0
                )
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (text?.trim()?.isNotEmpty() == true && TextUtil.graphemeLength(
                    s.trim().toString()
                ) > getMinimumQueryTextLength()
            ) {
                subject.onNext(text.toString())
            } else if (text?.trim()?.isEmpty() == true) {
                subject.onNext("")
            }
        }
    }

    init {
        val typedArray =
            getContext().theme.obtainStyledAttributes(
                attrs,
                R.styleable.SearchView,
                0,
                0
            )
        try {
            setDebounceTime(
                typedArray.getInt(
                    R.styleable.SearchView_debounceTime,
                    300
                )
            )
            setMinimumQueryTextLength(
                typedArray.getInt(
                    R.styleable.SearchView_minimumQueryTextLength,
                    1
                )
            )

        } finally {
            typedArray.recycle()
        }
    }

    private fun setListener() {
        addTextChangedListener(searchTextWatcher)
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callActionOnSearchButtonClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun cleanUpResources() {
        compositeDisposable.clear()
        removeTextChangedListener(searchTextWatcher)
    }

    private fun callActionOnSearchButtonClick() {
        KeyboardUtils.hideKeyboard(this)
    }

    private fun setUpSearchObservable() {
        compositeDisposable.add(
            subject
                .debounce(getDebounceTime().toLong(), TimeUnit.MILLISECONDS)
                .filter { text ->
                    return@filter text.trim()
                        .isEmpty() || TextUtil.graphemeLength(text.trim()) > getMinimumQueryTextLength()
                }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    if (mQueryListener != null) {
                        mQueryListener?.onQuery(result)
                    } else {
                        Toast.makeText(context, "Interface is not implemented", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    fun getDebounceTime(): Int {
        return this.debounceTime
    }

    fun getMinimumQueryTextLength(): Int {
        return this.minimumQueryTextLength
    }

    fun setQueryListener(queryListener: QueryListener) {
        this.mQueryListener = queryListener
    }

    fun setDebounceTime(debounceTime: Int) {
        this.debounceTime = debounceTime
    }

    fun setMinimumQueryTextLength(length: Int?) {
        this.minimumQueryTextLength = length ?: MIN_TEXT_LENGTH
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                handleTouchEvent(event)
                performClick()
                return true
            }
        }
        return false
    }

    private fun handleTouchEvent(event: MotionEvent) {
        val drawableRight = 2
        val rightDrawable = this.compoundDrawables[drawableRight]
        if (event.action == MotionEvent.ACTION_UP && rightDrawable != null) {
            if (this.text?.isNotEmpty() == true && event.rawX >= (this.right - rightDrawable.bounds.width() - 15.dpToPx())) {
                this.setText("")
                KeyboardUtils.hideKeyboard(this)
            } else if (event.rawX < (this.right - rightDrawable.bounds.width())) {
                this.requestFocus()
                KeyboardUtils.showKeyboard(this)
            }
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setListener()
        setUpSearchObservable()
    }

    override fun onDetachedFromWindow() {
        cleanUpResources()
        super.onDetachedFromWindow()
    }

    interface QueryListener {
        fun onQuery(query: String)
    }
}