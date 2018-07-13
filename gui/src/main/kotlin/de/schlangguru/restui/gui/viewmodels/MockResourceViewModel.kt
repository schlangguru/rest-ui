package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.UpdateMockResourceAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.server.ResponseStrategy
import de.schlangguru.restui.app.server.ScriptedResponseStrategy
import de.schlangguru.restui.app.server.SequentialResponseStrategy
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.observable
import tornadofx.onChange

/**
 * ViewModel for the [MockResource] Details.
 */
class MockResourceViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<MockResource>() {
    private val mockResourceID = bind (MockResource::id)
    val responseStrategy = bind (MockResource::responseStrategy)
    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
    val responses = bind { SimpleListProperty<MockResponse>(item?.responses?.observable()) }

    /** The ViewModel for the [ResponseStrategy] Details of the [MockResource]. Will be updated by this viewmodel. */
    private val responseStrategyViewModel: ResponseStrategyViewModel by inject()

    val availableMethods = listOf("GET", "POST", "PUT", "DELETE", "HEAD").observable()

    init {
        responseStrategy.onChange { responseStrategyViewModel.item = it }
        responseStrategyViewModel.type.onChange { responseStrategyTypeChanged(it) }
    }

    /**
     * Executed when the user selected another [ResponseStrategy] type in the combobox.
     * Exchanges the [responseStrategy] property of this viewmodel with a new [ResponseStrategy]
     * representing the selcted type.
     */
    private fun responseStrategyTypeChanged(type: ResponseStrategyType?) {
        when(type) {
            ResponseStrategyType.Scripted -> responseStrategy.value = ScriptedResponseStrategy("")
            ResponseStrategyType.Sequential -> responseStrategy.value = SequentialResponseStrategy()
        }
    }

    /**
     * Dispatches a [UpdateMockResourceAction] to update the
     * currently viewed [MockResource] in the [AppStore].
     */
    override fun onCommit() {
        store.dispatch(UpdateMockResourceAction(MockResource(
                mockResourceID.value,
                path.value,
                method.value,
                SequentialResponseStrategy(), //TODO
                responses.value
        )))
    }
}

/**
 * ViewModel for a [MockResponse].
 */
class MockResponseViewModel: ItemViewModel<MockResponse>() {
    val name = bind (MockResponse::name)
    val statusCode = bind (MockResponse::statusCode)
    val contentType = bind (MockResponse::contentType)
    val content = bind (MockResponse::content)

    override fun onCommit() {
        item = MockResponse(name.value, statusCode.value, contentType.value, content.value)
    }
}

/**
 * ViewModel for a [ResponseStrategy].
 */
class ResponseStrategyViewModel: ItemViewModel<ResponseStrategy>() {

    val type = bind { computeTypeProperty(item) }
    val script = bind { computeScriptProperty(item) }
    val isEditable =  bind { computeIsEditableProperty(item) }

    val availableTypes = listOf(ResponseStrategyType.Sequential , ResponseStrategyType.Scripted)

    private fun computeTypeProperty(responseStrategy: ResponseStrategy?): SimpleObjectProperty<ResponseStrategyType> {
        return when (responseStrategy) {
            is ScriptedResponseStrategy -> SimpleObjectProperty(ResponseStrategyType.Scripted)
            is SequentialResponseStrategy -> SimpleObjectProperty(ResponseStrategyType.Sequential)
            else -> SimpleObjectProperty(ResponseStrategyType.Unknown)
        }
    }

    private fun computeScriptProperty(responseStrategy: ResponseStrategy?): SimpleStringProperty {
        return when (responseStrategy) {
            is ScriptedResponseStrategy -> SimpleStringProperty(responseStrategy.script)
            else -> SimpleStringProperty()
        }
    }

    private fun computeIsEditableProperty(responseStrategy: ResponseStrategy?): SimpleBooleanProperty {
        return when (responseStrategy) {
            is ScriptedResponseStrategy -> SimpleBooleanProperty(true)
            else -> SimpleBooleanProperty(false)
        }
    }

    override fun onCommit() {
        item = when (type) {
            ResponseStrategyType.Scripted -> ScriptedResponseStrategy(script.value)
            else -> SequentialResponseStrategy()
        }
    }
}

/**
 * Enum for all available [ResponseStrategy] types, that can be
 * selected in the GUI.
 */
enum class ResponseStrategyType {
    Sequential, Scripted, Unknown
}