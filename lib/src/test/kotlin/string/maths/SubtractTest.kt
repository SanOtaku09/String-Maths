package string.maths

import org.junit.Assert
import org.junit.Test
import string.maths.Subtract.subtract
import kotlin.random.Random

class SubtractTest {
    @Test
    fun test() {
        for ( i in 0 until 100000 ) {
            val num1 = Random.nextInt().toLong()
            val num2 = Random.nextInt().toLong()
            Assert.assertEquals(
                ( num1 - num2 ).toString() ,
                ( num1 subtract  num2 ).toString()
            )
        }
    }
}