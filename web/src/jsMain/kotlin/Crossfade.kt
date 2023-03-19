import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import kotlin.js.Date

@Composable
fun Crossfade(attrs: AttrBuilderContext<*> = {}, content: @Composable () -> Unit) {


    Div(attrs = attrs) {
            Div(
                attrs = {
                    style {
                        width(100.percent)
                        height(100.percent)
                        position(Position.Absolute)
                        top(0.px)
                        left(0.px)
                    }
                }
            ) {
                content()
            }
    }
}

private fun interface Easing {
    fun transform(fraction: Float): Float
}
