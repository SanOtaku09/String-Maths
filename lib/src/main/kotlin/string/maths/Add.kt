package string.maths

import string.maths.Subtract.subtract
import string.maths.core.ProcessedData

object Add {

    infix fun Any.add( toAdd : Any ) : ProcessedData = ProcessedData( this , toAdd ).run {
        balancedDigitInDecimalPrecision()
        add()
        addPrecision( maxPrecision )
        trimAndFormatNumber()
        this
    }

    internal fun ProcessedData.add() {

        operation = '+'

        if ( num1IsNegative != num2IsNegative ) {
            if ( num1IsNegative ) {
                num1IsNegative = false
            } else {
                swapValues()
                num1IsNegative = false
            }
            subtract()
            return
        }

        result = StringBuilder( num1 ) ; result.reverse() ; num2.reverse() ; var index = 0 ; var carry = 0
        for ( digit in num2 ) {
            carry += digit - '0'
            if ( index < result.length ) {
                carry += result[index] - '0'
                result[index] = (carry%10).toString()[0]
            } else result.append( carry % 10 )
            carry /= 10
            index++
        }
        while ( carry != 0 ) {
            if ( index < result.length ) {
                carry += result[index] - '0'
                result[index] = ( carry % 10 ).toString()[0]
            } else result.append( carry % 10 )
            carry /= 10
            index++
        }
        if ( num1IsNegative ) result.append( '-' )
        num2.reverse() ; result.reverse()
    }

}