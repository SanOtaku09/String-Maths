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

    // operation name
    var operation = '?'

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

    fun balancedDigitInDecimalPrecision() {
        if ( ! containsDecimalPrecision ) return
        repeat( maxPrecision - decimalPrecisionNum1 ) { num1.append( '0' ) }
        repeat( maxPrecision - decimalPrecisionNum2 ) { num2.append( '0' ) }
    }

    fun trimAndFormatNumber() {
        if ( result.matches( "-?0+".toRegex() ) || result.matches( "-?0+\\.0+".toRegex() ) ) return
        var start = 0
        val sign = if ( ! result.matches( "[+-].+".toRegex() ) ) "" else { start++ ; result[0].toString() }
        var end = result.length-1
        while ( result[start] == '0' ) start++
        if ( result.contains( "." ) ) {
            while ( result[end] == '0' ) end--
        }
        result = StringBuilder( ( sign + result.substring( start , end+1 ).fixDotsPosition() ) )
    }

    fun addPrecision( precisionValue : Int ) {
        if ( ! containsDecimalPrecision ) return
        val sign = if ( result.matches( "[+-].+".toRegex() ) ) result[0].toString() else ""
        val start = if ( result.matches( "[+-].+".toRegex() ) ) 1 else 0
        result = StringBuilder(
            sign + result.substring( start ).run {
                if ( precisionValue == length ) {
                    "0.$result"
                } else if ( precisionValue < length ) {
                    substring( 0 , length - precisionValue ) + "." + substring( length - precisionValue )
                } else {
                    val zeroes = StringBuilder()
                    repeat( precisionValue - length ) { zeroes.append( '0' ) }
                    "0.$zeroes$result"
                }
            }
        )
    }

    fun optimiseForSubtraction() {
        num2IsNegative = !num2IsNegative
        if ( num2.length > num1.length ) swapValues()
        else if ( num1.length == num2.length ) {
            var index = 0
            while ( index < num1.length ) {
                if ( num2[index] > num1[index] ) {
                    swapValues()
                    break
                } else if ( num1[index] > num2[index] ) break
                index++
            }
        }
    }

    fun swapValues() {
        num2 = num1.also { num1 = num2 }
        num1IsNegative = num2IsNegative.also { num2IsNegative = num1IsNegative }
        originalNum1 = originalNum2.also { originalNum2 = originalNum1 }
        decimalPrecisionNum1 = decimalPrecisionNum2.also { decimalPrecisionNum2 = decimalPrecisionNum1 }
    }

    override fun toString(): String = result.toString()

    fun showDetails() : String {
        return  """
                |:::   $originalNum1   :::
                |Value                      : $num1
                |Sign                       : ${ if ( num1IsNegative ) "-" else "+" }
                |Decimal Precision          : $decimalPrecisionNum1
                |
                |:::   $originalNum2   :::
                |Value                      : $num2
                |Sign                       : ${ if ( num2IsNegative ) "-" else "+" }
                |Decimal Precision          : $decimalPrecisionNum2
                |
                |:::   Over All   :::
                |Contains Decimal Precision : $containsDecimalPrecision
                |Operation Performed        : ${operation.let { if ( operation == '?' ) "No operation has been performed Yet" else operation }}
                |Result                     : ${result.let { if ( result.length == 0 ) "No operation has been performed Yet" else result }}
            """.trimMargin()
    }

}