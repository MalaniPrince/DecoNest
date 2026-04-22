package princetechlabs.deconest.ui.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    companion object {
        private const val PREFS_NAME = "DecoNest"
        private const val USER_EMAIL = "UserEmail"
        private const val USER_NAME = "UserName"
        private const val USER_PHONE = "UserPhone"
        private const val KEY_OnBord = "chintu"
        private const val OnBoard_Show = "true"
        private const val KEY_DARK_MODE = "DarkMode"

        fun getSharedPrefs(context: Context): SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        fun getUserEmail(context: Context): String? =
            getSharedPrefs(context).getString(USER_EMAIL, null)

        fun setUserEmail(context: Context, email: String?) =
            getSharedPrefs(context).edit().putString(USER_EMAIL, email).commit()

        fun getOnBoardShow(context: Context): Boolean =
            getSharedPrefs(context).getBoolean(KEY_OnBord, false)

        fun setOnBoardShow(context: Context, onShow: Boolean) =
            getSharedPrefs(context).edit().putBoolean(KEY_OnBord, onShow).commit()

        fun isUserLoggedIn(context: Context): Boolean {
            val userEmail = getUserEmail(context)
            return !userEmail.isNullOrEmpty()
        }

        fun getOnBoarding(context: Context): Boolean =
            getSharedPrefs(context).getBoolean(OnBoard_Show, false)

        fun setOnBoarding(context: Context, onShow: Boolean) =
            getSharedPrefs(context).edit().putBoolean(OnBoard_Show, onShow).commit()

        fun getName(context: Context): String? =
            getSharedPrefs(context).getString(USER_NAME, null)

        fun setName(context: Context, name: String?) =
            getSharedPrefs(context).edit().putString(USER_NAME, name).commit()

        fun getPhone(context: Context): String? =
            getSharedPrefs(context).getString(USER_PHONE, null)

        fun setPhone(context: Context, phone: String?) =
            getSharedPrefs(context).edit().putString(USER_PHONE, phone).commit()

        fun isDarkMode(context: Context): Boolean =
            getSharedPrefs(context).getBoolean(KEY_DARK_MODE, false)

        fun setDarkMode(context: Context, enabled: Boolean) =
            getSharedPrefs(context).edit().putBoolean(KEY_DARK_MODE, enabled).commit()

        fun clear(context: Context) =
            getSharedPrefs(context).edit().clear().commit()

        fun clearAll(context: Context) =
            getSharedPrefs(context).edit().clear().commit()
    }
}
