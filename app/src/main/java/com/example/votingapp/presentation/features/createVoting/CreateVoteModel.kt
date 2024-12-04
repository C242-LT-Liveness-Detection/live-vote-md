package com.example.votingapp.presentation.features.createVoting

import android.util.Log
import androidx.compose.material3.rememberDatePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.votingapp.data.repositories.VoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import javax.inject.Inject


@HiltViewModel
class CreateVoteModel @Inject constructor(
    private val voteRepository: VoteRepository
) : ViewModel() {

    val createVoteUiInfo by lazy {
        MutableStateFlow(
            CreateVoteUiInfo(
                "",
                "",
                listOf(""),
                true,
                null,
                null
            )
        )
    }

    val showCalendar = MutableStateFlow(false)
    val showTimePicker = MutableStateFlow(false)

    val successMessage = MutableStateFlow<String?>(null)
    val errorMessage = MutableStateFlow<String?>(null)

    fun onEvent(event: CreateVoteEvent) {
        when (event) {
            is CreateVoteEvent.OnValueChange -> {
                onValueChange(event.value, event.field)
            }

            is CreateVoteEvent.ToggleCalendar -> {
                showCalendar.value = event.show
            }

            is CreateVoteEvent.OnDateSelected -> {
                createVoteUiInfo.value = createVoteUiInfo.value.copy(endDate = event.date)
            }

            is CreateVoteEvent.ToggleTimePicker -> {
                showTimePicker.value = event.show
            }

            is CreateVoteEvent.OnTimeSelected -> {
                createVoteUiInfo.value = createVoteUiInfo.value.copy(endTime = event.time)
            }

            is CreateVoteEvent.OptionOnChage -> {
                onOptionChange(event.value, event.index)
            }

            is CreateVoteEvent.CreateVote -> {
                createVote()
            }

            is CreateVoteEvent.ClearError -> {
                errorMessage.value = null
            }

            is CreateVoteEvent.ClearSuccess -> {
                successMessage.value = null
            }

        }


    }

    private fun onOptionChange(value: String, index: Int) {
        val options = createVoteUiInfo.value.options.toMutableList()
        options[index] = value
        createVoteUiInfo.value = createVoteUiInfo.value.copy(options = options)
        if (index == options.size - 1 && value.isNotEmpty()) {
            options.add("")
            createVoteUiInfo.value = createVoteUiInfo.value.copy(options = options)
        } else {
            if (value.isEmpty() && options.size > 1) {
                options.removeAt(index)
                createVoteUiInfo.value = createVoteUiInfo.value.copy(options = options)
            }
        }
    }

    private fun onValueChange(value: String, field: String) {
        when (field) {
            "title" -> {
                createVoteUiInfo.value = createVoteUiInfo.value.copy(title = value)
            }

            "question" -> {
                createVoteUiInfo.value = createVoteUiInfo.value.copy(question = value)
            }

            "allowMultipleSelection" -> {
                createVoteUiInfo.value =
                    createVoteUiInfo.value.copy(allowMultipleSelection = value.toBoolean())
            }


        }
    }

    private fun createVote() {
        viewModelScope.launch {
            val endDate =
                "${SimpleDateFormat("dd/MM/yyyy").format(createVoteUiInfo.value.endDate)}:${createVoteUiInfo.value.endTime}"

            val endDateIso = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(
                SimpleDateFormat("dd/MM/yyyy:HH:mm").parse(endDate)
            )
            try {
                val response = voteRepository.createVote(
                    title = createVoteUiInfo.value.title,
                    question = createVoteUiInfo.value.question,
                    options = createVoteUiInfo.value.options,
                    isMultipleChoice = createVoteUiInfo.value.allowMultipleSelection,
                    endDate = endDateIso,
                )
                successMessage.value = "Yeay, Berhasil"
            } catch (e: Exception) {
                errorMessage.value = e.message
                Log.e("CreateVoteModel", "createVote: ${e.message}")

            } catch (e: HttpException) {
                errorMessage.value = e.message()
                Log.e("CreateVoteModel", "createVote: ${e.message}")

            }
        }

    }
}

data class CreateVoteUiInfo(
    val title: String,
    val question: String,
    val options: List<String>,
    val allowMultipleSelection: Boolean,
    val endDate: Long?,
    val endTime: String?
)


sealed interface CreateVoteEvent {
    data class OnValueChange(val value: String, val field: String) : CreateVoteEvent
    data class ToggleCalendar(val show: Boolean) : CreateVoteEvent
    data class OnDateSelected(val date: Long) : CreateVoteEvent
    data class OnTimeSelected(val time: String) : CreateVoteEvent
    data class ToggleTimePicker(val show: Boolean) : CreateVoteEvent
    data class OptionOnChage(val value: String, val index: Int) : CreateVoteEvent
    data object CreateVote : CreateVoteEvent

    data object ClearError : CreateVoteEvent
    data object ClearSuccess : CreateVoteEvent

}