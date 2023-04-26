package com.example.araccessories.ui.features.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.useCases.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel  @Inject constructor(private val loginUseCase: UserLoginUseCase) : ViewModel(){
    var userEmail = MutableLiveData<String>()
    var userPassword = MutableLiveData<String>()
    var confirmedUser = User()
    var observer = MutableLiveData(1)
    fun login(){
        viewModelScope.launch {
            if (userEmail.value?.isEmpty() == true || userPassword.value?.isEmpty() == true
            ) {
                return@launch
            } else {

                val user = User(email = userEmail.value, password = userPassword.value)

                confirmedUser = loginUseCase.loginUser(user)!!
                    observer.value = observer.value?.inc()
                    observer.value = observer.value?.dec()
            }

        }
        }
    }
