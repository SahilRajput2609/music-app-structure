package com.musicapp.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
    val duration: Long = 0,
    val currentTrack: Track? = null,
    val isShuffled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.NONE
)

enum class RepeatMode { NONE, ONE, ALL }

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val coverUrl: String
)

class PlayerViewModel : ViewModel() {
    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()
    
    fun playPause() {
        viewModelScope.launch {
            _playerState.value = _playerState.value.copy(isPlaying = !_playerState.value.isPlaying)
        }
    }
    
    fun next() {
        // Implementation for next track
    }
    
    fun previous() {
        // Implementation for previous track
    }
    
    fun toggleShuffle() {
        _playerState.value = _playerState.value.copy(isShuffled = !_playerState.value.isShuffled)
    }
    
    fun toggleRepeat() {
        val newMode = when (_playerState.value.repeatMode) {
            RepeatMode.NONE -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.NONE
        }
        _playerState.value = _playerState.value.copy(repeatMode = newMode)
    }
}
