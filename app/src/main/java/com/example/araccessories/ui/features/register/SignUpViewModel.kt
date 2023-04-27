package com.example.araccessories.ui.features.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserRegister
import com.example.araccessories.domain.useCases.UserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val loginUseCase: UserAccountUseCase) :
    ViewModel() {
    var userName = MutableLiveData<String>()
    var userEmail = MutableLiveData<String>()
    var userPassword = MutableLiveData<String>()
    var userPhoneNumber = MutableLiveData<String>()
    var observer = MutableLiveData(1)
    var validator = MutableLiveData(1)
    var badRequest = MutableLiveData(1)

    fun register() {
        viewModelScope.launch {
            if (userName.value?.isEmpty() == true || userEmail.value?.isEmpty() == true || userPassword.value?.isEmpty() == true ||
                userEmail.value?.contains("@") == false ||
                userEmail.value?.contains(".com") == false ||
                        userPhoneNumber.value?.isEmpty() ==true
            ) {
                manipulateLiveData(validator)
                return@launch
            } else {
                val newUser = UserRegister(firstName = userName.value, email = userEmail.value, password = userPassword.value, phoneNumber = userPhoneNumber.value
                , gender = ".", lastName = ".", birthDate = "."
                )
                if (loginUseCase.registerNewUser(newUser)) {
                    resetData()
                   manipulateLiveData(observer)

                }
                else{
                    manipulateLiveData(badRequest)

                }
            }


        }

    }
    private fun manipulateLiveData(liveData: MutableLiveData<Int>) {
        liveData.value = liveData.value?.inc()
        liveData.value = liveData.value?.dec()
    }

    private fun resetData() {
        userEmail.value = ""
        userPassword.value = ""
        userPhoneNumber.value=""
        userName.value=""
    }

}