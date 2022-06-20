package string.maths

import string.maths.core.DataProcessor
object Multiply {

    infix fun Any.multiply(num: Any): StringBuilder {
        DataProcessor(this, num).run {
            if ( num1[0] == '0' || num2[0] == '0' ) return StringBuilder( "0" )
            return num1.multiply(num2, num1IsNegative, num2IsNegative).let { result ->
                if ( ! containsDecimalPrecision ) result
                else {
                    val sumPrecision = decimalPrecisionNum1 + decimalPrecisionNum2
                    val sign = if ( result.matches( "[+-].+".toRegex() ) ) result[0].toString() else ""
                    val start = if ( result.matches( "[+-].+".toRegex() ) ) 1 else 0
                    StringBuilder(
                        sign + result.substring( start ).run {
                            if ( sumPrecision == length ) {
                                "0.$result"
                            } else if ( sumPrecision < length ) {
                                substring( 0 , length - sumPrecision ) + "." + substring( length - sumPrecision )
                            } else {
                                val zeroes = StringBuilder()
                                repeat( sumPrecision - length ) { zeroes.append( '0' ) }
                                "0.$zeroes$result"
                            }
                        }
                    )
                }
            }.trimAndFormatNumber()
        }
    }

    fun StringBuilder.multiply(num: StringBuilder, thisIsNegative: Boolean, numIsNegative: Boolean): StringBuilder {
        val product : StringBuilder = StringBuilder()
        var level = 0 ; reverse() ; num.reverse()
        for ( i in this ) {
            var carry = 0 ; var index = level
            for ( j in num ) {
                var sum = ( i - '0' ) * ( j - '0' ) + carry
                if ( index < product.length ) {
                    sum += product[index] - '0'
                    product[index] = ( sum % 10 ).toString()[0]
                } else product.append( sum % 10 )
                carry = sum/10
                index++
            }
            while ( carry != 0 ) {
                if ( index < product.length ) {
                    carry += this[index] - '0'
                    this[index] = ( carry % 10 ).toString()[0]
                } else product.append( carry % 10 )
                carry /= 10
                index++
            }
            level++
        }
        if ( thisIsNegative != numIsNegative ) product.append( '-' )
        return product.reverse()
    }

}