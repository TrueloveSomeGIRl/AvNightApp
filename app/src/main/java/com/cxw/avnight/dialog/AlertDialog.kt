package com.cxw.avnight.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.cxw.avnight.R


/**
 * Description:  自定义万能的  Dialog
 */

class AlertDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    private val mAlert: AlertController = AlertController(this, window)


    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    fun setText(viewId: Int, text: CharSequence) {
        mAlert.setText(viewId, text)
    }

    fun <T : View> getView(viewId: Int): T? {
        return mAlert.getView(viewId)
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    fun setOnclickListener(viewId: Int, listener: View.OnClickListener) {
        mAlert.setOnclickListener(viewId, listener)
    }


    class Builder
    /**
     * Creates a builder for an alert dialog that uses an explicit theme
     * resource.
     *
     *
     * The specified theme resource (`themeResId`) is applied on top
     * of the parent `context`'s theme. It may be specified as a
     * style resource containing a fully-populated theme, such as
     * [android.R.style.Theme_Material_Dialog], to replace all
     * attributes in the parent `context`'s theme including primary
     * and accent colors.
     *
     *
     * To preserve attributes such as primary and accent colors, the
     * `themeResId` may instead be specified as an overlay theme such
     * as [android.R.style.ThemeOverlay_Material_Dialog]. This will
     * override only the window attributes necessary to style the alert
     * window as a dialog.
     *
     *
     * Alternatively, the `themeResId` may be specified as `0`
     * to use the parent `context`'s resolved value for
     * [android.R.attr.alertDialogTheme].
     *
     * @param context    the parent context
     * @param themeResId the resource ID of the theme against which to inflate
     * this dialog, or `0` to use the parent
     * `context`'s default alert dialog theme
     */
    @JvmOverloads constructor(context: Context, themeResId: Int = R.style.dialog) {

        private val P: AlertController.AlertParams = AlertController.AlertParams(context, themeResId)


        /**
         * Sets a custom view to be the contents of the alert dialog.
         *
         *
         * When using a pre-Holo theme, if the supplied view is an instance of
         * a [ListView] then the light background will be used.
         *
         *
         * **Note:** To ensure consistent styling, the custom view
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via [.getContext].
         *
         * @param view the view to use as the contents of the alert dialog
         * @return this Builder object to allow for chaining of calls to set
         * methods
         */
        fun setContentView(view: View): Builder {
            P.mView = view
            P.mViewLayoutResId = 0
            return this
        }

        // 设置布局内容的layoutId
        fun setContentView(layoutId: Int): Builder {
            P.mView = null
            P.mViewLayoutResId = layoutId
            return this
        }

        // 设置文本
        fun setText(viewId: Int, text: CharSequence): Builder {
            P.mTextArray.put(viewId, text)
            return this
        }

        // 设置点击事件
        fun setOnClickListener(view: Int, listener: View.OnClickListener): Builder {
            P.mClickArray.put(view, listener)
            return this
        }

        // 配置一些万能的参数
        fun fullWidth(): Builder {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画
         * @return
         */
        fun formBottom(isAnimation: Boolean): Builder {
            if (isAnimation) {
                P.mAnimations = R.style.dialog_from_bottom_anim
            }
            P.mGravity = Gravity.BOTTOM
            return this
        }


        /**
         * 设置Dialog的宽高
         * @param width
         * @param height
         * @return
         */
        fun setWidthAndHeight(width: Int, height: Int): Builder {
            P.mWidth = width
            P.mHeight = height
            return this
        }

        /**
         * 添加默认动画
         * @return
         */
        fun addDefaultAnimation(): Builder {
            P.mAnimations = R.style.dialog_scale_anim
            return this
        }

        /**
         * 设置动画
         * @param styleAnimation
         * @return
         */
        fun setAnimations(styleAnimation: Int): Builder {
            P.mAnimations = styleAnimation
            return this
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setCancelable(cancelable: Boolean): Builder {
            P.mCancelable = cancelable
            return this
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         *
         * Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * [setOnDismissListener][.setOnDismissListener].
         * @see .setCancelable
         * @see .setOnDismissListener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            P.mOnCancelListener = onCancelListener
            return this
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            P.mOnDismissListener = onDismissListener
            return this
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener): Builder {
            P.mOnKeyListener = onKeyListener
            return this
        }


        /**
         * Creates an [AlertDialog] with the arguments supplied to this
         * builder.
         *
         *
         * Calling this method does not display the dialog. If no additional
         * processing is needed, [.show] may be called instead to both
         * create and display the dialog.
         */
        fun create(): AlertDialog {
            // Context has already been wrapped with the appropriate theme.
            val dialog = AlertDialog(P.mContext, P.mThemeResId)
            P.apply(dialog.mAlert)
            dialog.setCancelable(P.mCancelable)
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true)
            }
            dialog.setOnCancelListener(P.mOnCancelListener)
            dialog.setOnDismissListener(P.mOnDismissListener)
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener)
            }
            return dialog
        }


        /**
         * Creates an [AlertDialog] with the arguments supplied to this
         * builder and immediately displays the dialog.
         *
         *
         * Calling this method is functionally identical to:
         * <pre>
         * AlertDialog dialog = builder.create();
         * dialog.show();
        </pre> *
         */
        fun show(): AlertDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }
    }
    /**
     * Creates a builder for an alert dialog that uses the default alert
     * dialog theme.
     *
     *
     * The default alert dialog theme is defined by
     * [android.R.attr.alertDialogTheme] within the parent
     * `context`'s theme.
     *
     * @param context the parent context
     */
}

