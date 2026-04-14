package princetechlabs.deconest.ui.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object CustomDialog {

    // Basic toast — kept for backward compat
    fun ShowToastMessage(context: Context, Message: String) {
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Confirmation dialog with a positive and negative button.
     * Use for destructive actions like logout, delete, clear cart.
     */
    fun showConfirm(
        context: Context,
        title: String,
        message: String,
        positiveText: String = "Yes",
        negativeText: String = "Cancel",
        onConfirm: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { dialog, _ ->
                dialog.dismiss()
                onConfirm()
            }
            .setNegativeButton(negativeText) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    /**
     * Simple info dialog. Use for "Coming Soon" features or one-line notices.
     */
    fun showInfo(
        context: Context,
        title: String,
        message: String,
        buttonText: String = "OK"
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    /**
     * Sort dialog — shows a list of options and returns the selected index.
     */
    fun showSortDialog(
        context: Context,
        title: String,
        options: Array<String>,
        onSelected: (Int) -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setItems(options) { dialog, which ->
                dialog.dismiss()
                onSelected(which)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
