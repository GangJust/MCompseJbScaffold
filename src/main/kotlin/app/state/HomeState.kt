package app.state

import app.model.HomeModel
import mvvm.AbstractState

class HomeState : AbstractState<HomeModel>() {
    override fun createModel(): HomeModel = HomeModel()


}