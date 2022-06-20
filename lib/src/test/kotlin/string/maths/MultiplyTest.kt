package string.maths

import org.junit.Assert
import org.junit.Test
import string.maths.Multiply.multiply
import kotlin.random.Random

class MultiplyTest {

    @Test
    fun test() {

        for ( i in 1 .. 100000 ) {
            val num1 = Random.nextInt()
            val num2 = Random.nextInt()
            val normal = ( num1.toLong()*num2.toLong() ).toString()
            val stringMaths = ( num1 multiply num2 ).toString()
            Assert.assertEquals( normal , stringMaths )
        }

    }

}