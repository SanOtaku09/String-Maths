package string.maths

import org.junit.Assert
import org.junit.Test
import string.maths.Add.add
import kotlin.random.Random

class AddTest {
    @Test
    fun test() {
        for ( i in 0 until 100000 ) {
            val num1 = Random.nextInt().toLong()
            val num2 = Random.nextInt().toLong()
            println( num1 )
            println( num2 )
            Assert.assertEquals(
                ( num1 + num2 ).toString() ,
                ( num1 add num2 ).toString()
            )
        }
    }
}