package mvvm


interface IState {

}

abstract class AbstractState<M : IModel> : IState {
    val model = createModel()

    //subclasses `createModel()` for data processing
    abstract fun createModel(): M
}