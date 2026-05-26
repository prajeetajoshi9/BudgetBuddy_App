package com.example.budgetbuddy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.repo.UserRepo

class UserViewModel(val repo : UserRepo) : ViewModel(){


    fun login(email:String, password:String, callback: (Boolean, String)-> Unit) {
        repo.login(email,password,callback)
    }
    fun register(email: String, password: String ,callback: (Boolean, String, String) -> Unit ){
        repo.register(email,password,callback)

    }
    fun addUser(id: String, model: UserModel, callback: (Boolean, String) -> Unit){
        repo.editProfile(id,model,callback)

    }
    fun forgetPassword(email: String , callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }
    fun editProfile(id: String , model: UserModel ,callback: (Boolean, String) -> Unit){
        repo.editProfile(id,model,callback)
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : MutableLiveData<Boolean>
        get() = _loading
    private val _users = MutableLiveData<UserModel?>()
    val user : MutableLiveData<UserModel?> get() = _users

    fun getUserById(id: String,
    ){
        loading.value = true
        repo.getUserById(id){
                success, message , data ->
            if(success ){
                _users.value = data
            } else{
                _users.value = null
                _loading.value = false
            }
        }
    }


    private val _allUsers = MutableLiveData<List<UserModel?>>()
    val allUsers: MutableLiveData<List<UserModel?>> get() = _allUsers

    fun getAllUser(){
        repo.getAllUser { success, message, data ->
            if (success){
                _loading.value = false
                _allUsers.value = data
            } else{
                _loading.value = false
                _allUsers.value = emptyList()
            }
        }
    }
    fun logout(callback: (Boolean, String) -> Unit){
        repo.logout (callback )
    }
    fun deleteUser(id: String , callback: (Boolean, String) -> Unit){
        repo.deleteUser(id,callback)
    }


}