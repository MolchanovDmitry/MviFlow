import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dmitry.molchanov.database.di.jsDatabase
import dmitry.molchanov.di.domainModule
import dmitry.molchanov.model.util.di.jsDomainModule
import dmitry.molchanov.presentation.di.presentationModule
import dmitry.molchanov.presentation.main.MainViewModel
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.TextArea
import org.koin.core.context.startKoin

@Composable
fun TodoRootUi(/*component: TodoRoot*/) {
    val koinApp = startKoin {
        modules(
            jsDomainModule,
            domainModule,
            jsDatabase,
            presentationModule
        )
    }
    Card(
        attrs = {
            style {
                position(Position.Absolute)
                height(700.px)
                property("max-width", 640.px)
                top(0.px)
                bottom(0.px)
                left(0.px)
                right(0.px)
                property("margin", auto)
            }
        }
    ) {
        val mainVM = remember {
            koinApp.koin.get<MainViewModel>()
        }

        Crossfade(
            attrs = {
                style {
                    width(100.percent)
                    height(100.percent)
                    position(Position.Relative)
                    left(0.px)
                    top(0.px)
                }
            },
            content = { TodoMainUi(mainVM) }
        )
    }
}
