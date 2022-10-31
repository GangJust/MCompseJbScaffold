package app.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import app.state.HomeState
import mvvm.AbstractView

class HomeUI : AbstractView<HomeState>() {
    override fun createState(): HomeState = HomeState()

    @Composable
    override fun viewCompose() {
        Text("Hello World!!")
    }
}