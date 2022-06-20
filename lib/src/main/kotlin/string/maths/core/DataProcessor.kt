package string.maths.core

import string.maths.core.Checker.checkFormat
import string.maths.core.Checker.fixDotsPosition

class DataProcessor {

    // original data
    var originalNum1 : String
    var originalNum2 : String

    // sign of data
    var num1IsNegative : Boolean = false
    var num2IsNegative : Boolean = false


    // data in stringBuilder format
    var num1 : StringBuilder
    var num2 : StringBuilder

    // decimal precision of numbers
    var containsDecimalPrecision : Boolean = false
    var decimalPrecisionNum1 : Int = 0
    var decimalPrecisionNum2 : Int = 0
    var maxPrecision : Int = 0

    constructor ( Num1 : Any , Num2 : Any ) {

        originalNum1 = Num1.toString().fixDotsPosition()
        originalNum2 = Num2.toString().fixDotsPosition()

        originalNum1.checkFormat()
        originalNum2.checkFormat()

        if ( originalNum1.contains( "." ) || originalNum2.contains( "." ) ) containsDecimalPrecision = true

        if ( originalNum1[0] == '-' ) num1IsNegative = true
        if ( originalNum2[0] == '-' ) num2IsNegative = true

        num1 = StringBuilder( originalNum1.replace( "+" , "" ).replace( "-" , "" ).replace( "." , "" ) )
        num2 = StringBuilder( originalNum2.replace( "+" , "" ).replace( "-" , "" ).replace( "." , "" ) )

        if ( ! containsDecimalPrecision ) return

        if ( originalNum1.contains( "." ) ) decimalPrecisionNum1 = originalNum1.replace( "+" , "" ).replace( "-" , "" ).let { it.length - it.indexOf( "." ) } - 1

        if ( originalNum2.contains( "." ) ) decimalPrecisionNum2 = originalNum2.replace( "+" , "" ).replace( "-" , "" ).let { it.length - it.indexOf( "." ) } - 1

        maxPrecision = if ( decimalPrecisionNum1 < decimalPrecisionNum2 ) decimalPrecisionNum2 else decimalPrecisionNum1

    }

    fun optimiseForAdditionAndSubtraction() {
        if ( ! containsDecimalPrecision ) return
        repeat( maxPrecision - decimalPrecisionNum1 ) { num1.append( '0' ) }
        repeat( maxPrecision - decimalPrecisionNum2 ) { num2.append( '0' ) }
    }

}