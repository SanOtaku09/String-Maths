package string.maths

import string.maths.core.ProcessedData
object Multiply {

    infix fun Any.multiply( num : Any ) : ProcessedData = ProcessedData( this , num ).run {
        multiply()
        if ( containsDecimalPrecision ) {
            val sumPrecision = decimalPrecisionNum1 + decimalPrecisionNum2
            val sign = if ( result.matches( "[+-].+".toRegex() ) ) result[0].toString() else ""
            val start = if ( result.matches( "[+-].+".toRegex() ) ) 1 else 0
            result = StringBuilder(
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
        trimAndFormatNumber()
        this
    }

    internal fun ProcessedData.multiply() {
        var level = 0 ; num1.reverse() ; num2.reverse()
        for ( i in num1 ) {
            var carry = 0 ; var index = level
            for ( j in num2 ) {
                var sum = ( i - '0' ) * ( j - '0' ) + carry
                if ( index < result.length ) {
                    sum += result[index] - '0'
                    result[index] = ( sum % 10 ).toString()[0]
                } else result.append( sum % 10 )
                carry = sum/10
                index++
            }
            while ( carry != 0 ) {
                if ( index < result.length ) {
                    carry += num1[index] - '0'
                    num1[index] = ( carry % 10 ).toString()[0]
                } else result.append( carry % 10 )
                carry /= 10
                index++
            }
            level++
        }
        if ( num1IsNegative != num2IsNegative ) result.append( '-' )
        result.reverse()
    }

}