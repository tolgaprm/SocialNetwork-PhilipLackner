package com.prmto.socialnetwork_philiplackner.feature_activity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.prmto.socialnetwork_philiplackner.feature_activity.domain.use_case.GetActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivities: GetActivitiesUseCase
) : ViewModel() {
//6367b3cec4e4cc0ccf405035
    val activities = getActivities().cachedIn(viewModelScope)

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.ClickedOnUser -> {

            }
            is ActivityEvent.ClickedOnParent -> {

            }
        }
    }
}