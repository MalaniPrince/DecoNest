package princetechlabs.deconest.ui.utils

import android.content.Context
import android.widget.Toast

object CustomDialog {
        fun ShowToastMessage(context: Context, Message: String) {
            Toast.makeText(context, Message, Toast.LENGTH_SHORT).show()

        }


}