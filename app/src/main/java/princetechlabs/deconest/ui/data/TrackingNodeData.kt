package princetechlabs.deconest.ui.data

data class TrackingNodeData(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val isLast: Boolean = false
)
