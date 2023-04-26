package com.example.araccessories.data.dataSource.localDataSource.entities

data class User(var email : String ,
                var password : String ,
                var firstName : String ,
                var lastName : String ,
                var phoneNumber : String ,
                var birthDate : String = "",
                var city : String = " ",
                var zipCode : String = " ",
                var streetAddress : String = " ",
                var floorNum : String = " ",
                var aptNum : String = " ",


                )
