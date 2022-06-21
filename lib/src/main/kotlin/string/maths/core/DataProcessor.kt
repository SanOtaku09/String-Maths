package string.maths.core

import string.maths.core.Checker.checkFormat
import string.maths.core.Checker.fixDotsPosition

class ProcessedData {

    // ans
    var result : StringBuilder = StringBuilder()

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

        num1 = Num1.toString().fixDotsPosition().run {
            checkFormat()
            if ( matches( "\\+?-?0+".toRegex() ) || matches( "\\+?-?0+\\.0+".toRegex() ) ) {
                originalNum1 = "0"
                return@run StringBuilder( originalNum1 )
            }
            if ( this[0] == '-' ) num1IsNegative = true
            var start = if ( matches( "[+-].+".toRegex() ) ) 1 else 0
            var end = length-1
            while ( this[start] == '0' ) start++
            if ( contains( "." ) ) {
                containsDecimalPrecision = true
                while ( this[end] == '0' ) end--
            }
            originalNum1 = substring( start , end+1 )
            StringBuilder( originalNum1.replace( "." , "" ) )
        }

        num2 = Num2.toString().fixDotsPosition().run {
            checkFormat()
            if ( matches( "\\+?-?0+".toRegex() ) || matches( "\\+?-?0+\\.0+".toRegex() ) ) {
                originalNum2 = "0"
                return@run StringBuilder( originalNum2 )
            }
            if ( this[0] == '-' ) num2IsNegative = true
            var start = if ( matches( "[+-].+".toRegex() ) ) 1 else 0
            var end = length-1
            while ( this[start] == '0' ) start++
            if ( contains( "." ) ) {
                containsDecimalPrecision = true
                while ( this[end] == '0' ) end--
            }
            originalNum2 = substring( start , end+1 )
            if ( originalNum2 == "" ) originalNum2 = "0"
            StringBuilder( originalNum2.replace( "." , "" ) )
        }

        if ( ! containsDecimalPrecision ) return

        originalNum1.run {
            if ( contains( "." ) ) decimalPrecisionNum1 = length - indexOf( "." ) - 1
        }

        originalNum2.run {
            if ( contains( "." ) ) decimalPrecisionNum2 = length - indexOf( "." ) - 1
        }

        maxPrecision = if ( decimalPrecisionNum1 < decimalPrecisionNum2 ) decimalPrecisionNum2 else decimalPrecisionNum1

    }

    fun optimiseForAdditionAndSubtraction() {
        if ( ! containsDecimalPrecision ) return
        repeat( maxPrecision - decimalPrecisionNum1 ) { num1.append( '0' ) }
        repeat( maxPrecision - decimalPrecisionNum2 ) { num2.append( '0' ) }
    }

    fun trimAndFormatNumber() {
        var start = 0
        val sign = if ( ! result.matches( "[+-].+".toRegex() ) ) "" else { start++ ; result[0].toString() }
        var end = result.length-1
        while ( result[start] == '0' ) start++
        if ( result.contains( "." ) ) {
            while ( result[end] == '0' ) end--
        }
        result = StringBuilder( ( sign + result.substring( start , end+1 ).fixDotsPosition() ) )
    }

    override fun toString(): String = result.toString()

}