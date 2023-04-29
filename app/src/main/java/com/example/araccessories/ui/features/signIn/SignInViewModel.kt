package com.example.araccessories.ui.features.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.localDataSource.sharedPrefrence.SharedPreference
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserLogin
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse
import com.example.araccessories.domain.useCases.UserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val loginUseCase: UserAccountUseCase) :
    ViewModel() {
    var userEmail = MutableLiveData<String>("")
    var userPassword = MutableLiveData<String>("")
    private var confirmedUser = UserResponse()
    var observer = MutableLiveData(1)
    var validator = MutableLiveData(1)
    var badRequest = MutableLiveData(1)


    fun login() {

        viewModelScope.launch {
            if (userEmail.value?.isEmpty() == true || userPassword.value?.isEmpty() == true
            ) {
                manipulateLiveData(validator)
                return@launch
            } else {
                val user = UserLogin(email = userEmail.value, password = userPassword.value)
                if (loginUseCase.loginUser(user) != null) {
                    confirmedUser = loginUseCase.loginUser(user)!!
                    SharedPreference.writeStringFromSharedPreference(
                        "token",
                        confirmedUser.token.toString()
                    )
                    resetData()
                    manipulateLiveData(observer)
                } else {
                    manipulateLiveData(badRequest)
                }
            }

        }
    }

    private fun manipulateLiveData(liveData: MutableLiveData<Int>) {
        liveData.value = liveData.value?.inc()

    }

    private fun resetData() {
        userEmail.value = ""
        userPassword.value = ""
    }
}
