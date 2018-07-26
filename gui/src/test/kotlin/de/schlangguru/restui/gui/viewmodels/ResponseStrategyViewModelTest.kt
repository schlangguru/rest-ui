package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.server.ScriptedResponseStrategy
import de.schlangguru.restui.app.server.SequentialResponseStrategy
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testfx.api.FxToolkit

class ResponseStrategyViewModelTest {
    private lateinit var viewModel: ResponseStrategyViewModel

    init {
        // Initilaize JavaFX
        FxToolkit.registerPrimaryStage()
    }

    @BeforeEach
    fun setup() {
        viewModel = ResponseStrategyViewModel()
    }

    @Test
    fun `it should commit the correct response strategy - sequential`() {
        viewModel.type.value = ResponseStrategyType.Sequential

        viewModel.commit()

        assertThat(viewModel.item, instanceOf(SequentialResponseStrategy::class.java))
    }

    @Test
    fun `it should commit the correct response strategy - scripted`() {
        viewModel.type.value = ResponseStrategyType.Scripted
        viewModel.script.value = "foo"

        viewModel.commit()

        assertThat(viewModel.item, instanceOf(ScriptedResponseStrategy::class.java))
        assertThat((viewModel.item as ScriptedResponseStrategy).script, `is`("foo"))
    }

    @Test
    fun `sequential response strategy should viewModel define as not editable`() {
        viewModel.item = SequentialResponseStrategy()

        assertThat(viewModel.isEditable.value, `is`(false))
    }

    @Test
    fun `scripted response strategy should viewModel define as editable`() {
        viewModel.item = ScriptedResponseStrategy("foo")

        assertThat(viewModel.isEditable.value, `is`(true))
    }
}