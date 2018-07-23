package de.schlangguru.restui.gui.viewmodels

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testfx.api.FxToolkit

class MockResponseViewModelTest {
    lateinit var viewModel: MockResponseViewModel

    init {
        // Initilaize JavaFX
        FxToolkit.registerPrimaryStage()
    }

    @BeforeEach
    fun setup() {
        viewModel = MockResponseViewModel()
    }

    @Test
    fun `it should commit a correct mocked response`() {
        viewModel.name.value = "MyResponse"
        viewModel.statusCode.value = 200
        viewModel.contentType.value = "MyContentType"
        viewModel.content.value = "MyContent"

        viewModel.commit()

        val commitedResponse = viewModel.item
        assertThat(commitedResponse.name, `is`("MyResponse"))
        assertThat(commitedResponse.statusCode, `is`(200))
        assertThat(commitedResponse.contentType, `is`("MyContentType"))
        assertThat(commitedResponse.content, `is`("MyContent"))
    }
}