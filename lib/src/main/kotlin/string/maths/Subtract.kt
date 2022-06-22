package string.maths

import string.maths.Add.add
import string.maths.core.ProcessedData

object Subtract {

    infix fun Any.subtract( num : Any ) : ProcessedData = ProcessedData( this , num ).run {
        balancedDigitInDecimalPrecision()
        subtract()
        addPrecision( maxPrecision )
        trimAndFormatNumber()
        this
    }

    internal fun ProcessedData.subtract() {

        operation = '-'

        if ( num1IsNegative != num2IsNegative ) {
            if ( num1IsNegative ) {
                num2IsNegative = true
                add()
            } else {
                num2IsNegative = false
                add()
            }
            return
        }

        if ( num1.toString() == num2.toString() ){
                optimiseForSubtraction()
            result.append( '0' )
            return
        }

        optimiseForSubtraction()

        result = StringBuilder( num1 ) ; var index1 = result.length ; var index2 = num2.length

        while ( index1-- > 0 && index2-- > 0 ) {
            if ( result[index1] >= num2[index2] ) result[index1] = ( result[index1] - num2[index2] ).toString()[0]
            else if ( result.substring( 0 , index1 ).matches( "0+".toRegex() ) ) result[index1] = ( num2[index2] - result[index1] ).toString()[0]
            else if ( index1 != 0 ) {
                result[index1] = ( 10 + ( result[index1] - num2[index2] ) ).toString()[0]
                // looking for number to borrow
                var borrower = index1-1
                while ( result[borrower] == '0' ) {
                    result[borrower] = '9'
                    borrower--
                }
                result[borrower] = ( result[borrower] - '1' ).toString()[0]
            } else result[index1] = ( num2[index2] - result[index1] ).toString()[0]
        }

        if ( num1IsNegative ) result.reverse().append('-').reverse()

    }

}