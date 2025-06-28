package princeinfotech.deconest.ui.utils

    import android.content.Context
    import android.content.SharedPreferences


    class PreferenceHelper {
        companion object {
            private const val PREFS_NAME = "DecoNest"
            private const val USER_EMAIL = "UserEmail"
            private const val USER_NAME = "UserName"
            private const val KEY_OnBord = "chintu"
            private const val KEY_USER_GENDER = "userGender"
            private const val KEY_PROFILE_IMAGE = "profileImage"
            private const val USER_ID = "UserID"
            private const val KEY_LAST_LATITUDE = "last_latitude"
            private const val KEY_LAST_LONGITUDE = "last_longitude"
            private const val KEY_LAST_ADDRESS = "last_address"
            private const val VERSION_NAME = "1"
            private const val OnBoard_Show ="true"

            fun getSharedPrefs(context: Context): SharedPreferences {
                return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            }

            fun getUserEmail(context: Context): String? {
                return getSharedPrefs(context).getString(USER_EMAIL, VERSION_NAME)
            }

            fun setUserEmail(context: Context, email: String?) {
                getSharedPrefs(context).edit().putString(USER_EMAIL, email).commit()
            }
            fun getOnBoardShow(context: Context): Boolean {
                return getSharedPrefs(context).getBoolean(KEY_OnBord,false)
            }

            fun setOnBoardShow(context: Context, onShow: Boolean) {
                getSharedPrefs(context).edit().putBoolean(KEY_OnBord, onShow).commit()
            }


            fun isUserLoggedIn(context: Context): Boolean {
                val userEmail = getUserEmail(context)
                return userEmail != null && userEmail != VERSION_NAME
            }

            fun getOnBoarding(context: Context): Boolean {
                return getSharedPrefs(context).getBoolean(OnBoard_Show,false)
            }


            fun setOnBoarding(context: Context, onShow: Boolean) {
                getSharedPrefs(context).edit().putBoolean(OnBoard_Show, onShow).commit()
            }

            /* fun saveProfileData(context: Context, profile: ProfileData?) {
                 getSharedPrefs(context).edit {
                     putString(USER_EMAIL, profile?.email)
                     putString(USER_NAME, profile?.name)
                     profile?.age?.let { putInt(KEY_USER_AGE, it) }
                     profile?.gender?.let { putString(KEY_USER_GENDER, it) }
                     profile?.imageUri?.let { putString(KEY_PROFILE_IMAGE, it) }
                     profile?.address?.let { putString(KEY_LAST_ADDRESS, it) }
                 }
             }

             fun getProfileData(context: Context): ProfileData {
                 val prefs = getSharedPrefs(context)
                 return ProfileData(
                     name = prefs.getString(USER_NAME, "") ?: "",
                     email = prefs.getString(USER_EMAIL, "") ?: "",
                     age = try {
                         prefs.getInt(KEY_USER_AGE, 0)
                     } catch (e: ClassCastException) {
                         prefs.getString(KEY_USER_AGE, "0")?.toIntOrNull() ?: 0
                     },
                     gender = try {
                         prefs.getString(KEY_USER_GENDER, null)
                     } catch (e: ClassCastException) {
                         null
                     }.toString(),
                     imageUri = prefs.getString(KEY_PROFILE_IMAGE, null).toString(),
                     address = prefs.getString(KEY_LAST_ADDRESS, null).toString()
                 )
             }

             fun setLastLocation(context: Context, locationData: LocationData) {
                 getSharedPrefs(context).edit {
                     putFloat(KEY_LAST_LATITUDE, locationData.latitude.toFloat())
                     putFloat(KEY_LAST_LONGITUDE, locationData.longitude.toFloat())
                 }
             }

             fun getLastLocation(context: Context): LocationData {
                 val prefs = getSharedPrefs(context)
                 return LocationData(
                     prefs.getFloat(KEY_LAST_LATITUDE, 0f).toDouble(),
                     prefs.getFloat(KEY_LAST_LONGITUDE, 0f).toDouble(),
                 )
             }*/

            fun clear(context: Context) {
                getSharedPrefs(context).edit().clear().commit()
            }
        }
    }