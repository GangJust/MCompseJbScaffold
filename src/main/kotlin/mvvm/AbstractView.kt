package mvvm

import androidx.compose.runtime.Composable


interface IView {

    //subclass implementation must add `@Composable` annotation
    //write layout in `viewCompose()` method
    @Composable
    fun viewCompose()
}

abstract class AbstractView<S : IState> : IView {
    val state: S = createState()

    abstract fun createState(): S
}

/**
 * 可直接调用该方法, 符合Compose编程习惯
 */
@Composable
fun <V : IView> ViewCompose(v: V) = v.viewCompose()