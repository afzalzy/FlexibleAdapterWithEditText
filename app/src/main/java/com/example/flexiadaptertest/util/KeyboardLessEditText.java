package com.example.flexiadaptertest.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Om Info on 03-May-16.
 */
public class KeyboardLessEditText extends AppCompatEditText
{
    private Context context;
    private static final Method mShowSoftInputOnFocus = getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);
    public boolean isTouchAllowed = true;
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("KeyboardLessEditText", "mOnClickListener");
            setCursorVisible(true);

        }
    };

    private OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            setCursorVisible(true);
            return false;
        }
    };

    public KeyboardLessEditText(Context context) {
        super(context);

        this.context = context;
        initialize();
    }

    public KeyboardLessEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public KeyboardLessEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initialize();
    }

    private void initialize() {

        if (requireToHideKeyBoard()) {
            //LogUtil.error("Custome C2 ");

            synchronized (this) {
                setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                setFocusableInTouchMode(true);
            }

            setOnClickListener(mOnClickListener);
            setOnLongClickListener(mOnLongClickListener);
            reflexSetShowSoftInputOnFocus(false); // Workaround.
            setSelection(getText().length());
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if (requireToHideKeyBoard()) {
            hideKeyboard();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean ret = super.onTouchEvent(event);
        //Must be done after super.onTouchEvent()
        if (requireToHideKeyBoard()) {
            hideKeyboard();
        }
        return ret && isTouchAllowed;
    }

    private void hideKeyboard() {
        final InputMethodManager imm = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null && imm.isActive(this)) {
            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }

    private void reflexSetShowSoftInputOnFocus(boolean show) {

        if (requireToHideKeyBoard()) {
            if (mShowSoftInputOnFocus != null) {
                invokeMethod(mShowSoftInputOnFocus, this, show);
            } else {
                // Use fallback method. Not tested.
                hideKeyboard();
            }
        }
    }

    /**
     * Returns method if available in class or superclass (recursively),
     * otherwise returns null.
     */
    public static Method getMethod(Class<?> cls, String methodName, Class<?>... parametersType) {
        Class<?> sCls = cls.getSuperclass();
        while (sCls != Object.class) {
            try {
                return sCls.getDeclaredMethod(methodName, parametersType);
            } catch (NoSuchMethodException e) {
                // Just super it again
            }
            sCls = sCls.getSuperclass();
        }
        return null;
//        throw new RuntimeException("Method not found " + methodName);
    }

    /**
     * Returns results if available, otherwise returns null.
     */
    public static Object invokeMethod(Method method, Object receiver, Object... args) {
        try {
            return method.invoke(receiver, args);
        } catch (IllegalArgumentException e) {
            Log.e("Safe invoke fail", "Invalid args", e);
        } catch (IllegalAccessException e) {
            Log.e("Safe invoke fail", "Invalid access", e);
        } catch (InvocationTargetException e) {
            Log.e("Safe invoke fail", "Invalid target", e);
        }

        return null;
    }

    private boolean requireToHideKeyBoard()
    {
            return true;
    }
}