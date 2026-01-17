package princetechlabs.deconest.data

data class CategoryData(
    val title: String,
    val desc: String,
    val image: Int,
    var tworecyclelist : List<CategoryTwo>,
    var expnad : Boolean = false
)
