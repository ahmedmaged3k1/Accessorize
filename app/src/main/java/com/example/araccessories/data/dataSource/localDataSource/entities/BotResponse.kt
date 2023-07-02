package com.example.araccessories.data.dataSource.localDataSource.entities

import com.example.araccessories.ui.core.utilities.Constants.OPEN_GOOGLE
import com.example.araccessories.ui.core.utilities.Constants.OPEN_SEARCH
import com.example.araccessories.ui.core.utilities.RecommendProducts
import com.example.araccessories.ui.core.utilities.SolveMath
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class BotResponse( private val listener: MessageClickListener) {
    interface MessageClickListener {
        fun onMessageClick(name: String)

    }


    fun basicResponses(_message: String): String {
        RecommendProducts.initializeRecommenders()
        val random = (0..2).random()
        val message = _message.lowercase(Locale.getDefault())
        if (message.contains("yellow sunglasses")) {
            listener.onMessageClick("Red Rouge")
        } else if (message.contains("red rouge")) {
            listener.onMessageClick("Yellow Sunglasses")
        } else if (message.contains("queen mask")) {
            listener.onMessageClick("Queen Mask ")
        } else if (message.contains("black hat")) {
            listener.onMessageClick("Mask Amirat")
        }
        else if (message.contains("green sunglasses")) {
            listener.onMessageClick("Red Earings")
        }
        else if (message.contains("red earings")) {
            listener.onMessageClick("Green Sunglasses")
        }
        return when {

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("Hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Buongiorno!"
                    else -> "error"
                }
            }
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Buongiorno!"
                    else -> "error"
                }
            }
            message.contains("hi") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Buongiorno!"
                    else -> "error"
                }
            }
            message.contains("Hi") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Buongiorno!"
                    else -> "error"
                }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            message.contains("help") && message.contains("google") -> {
                "I can help you by recommending for you what to buy"
            }


            //Search on the internet
            message.contains("search") -> {
                OPEN_SEARCH
            }

            message.contains("red rouge") -> {
                "People Often buy Yellow Sunglasses  with the red rouge  \n" +
                        "I will redirect you to this product\n"

            }

            message.contains("black hat") -> {
                "People Often buy Queen Mask with Black Hat     \n" +
                        "I will redirect you to this product\n"

            }

            message.contains("queen mask") -> {
                "People Often buy Black Hat with Queen Mask  \n" +
                        "I will redirect you to this product\n"

            }

            message.contains("yellow sunglasses") -> {
                "People Often buy red rouge with the Yellow Sunglasses  E\n" +
                        "I will redirect you to this product\n"

            }
            message.contains("red earings") -> {
                "People Often buy Green Sunglasses with Red Earings  \n" +
                        "I will redirect you to this product\n"

            }
            message.contains("green sunglasses") -> {
                "People Often buy Red Earings with Green Sunglasses   E\n" +
                        "I will redirect you to this product\n"

            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "I don't know "
                    else -> "error"
                }
            }
        }
    }


}