/**
 * This class ....
 *
 *
 * An instance contains 32 registers and methods to access and change them
 *
 * @author someone
 */

class Registers {
    val registers: IntArray = IntArray(NUMBEROFREGISTERS)

    // Constructor: an instance whose registers are set to 0

    init {
        for (i in registers!!.indices) {
            registers[i] = 0
        }
    }

    // Set register i to v.
    // Precondition: 0 <= i <= NUMBEROFREGISTERS

    fun setRegister(i: Int, v: Int) {
        registers[i] = v
    }

    fun getRegister(i: Int): Int {
        return registers!![i]
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is Registers) return false
        val other = o as Registers?
        if (!other!!.canEqual(this as Any)) return false
        return if (!java.util.Arrays.equals(this.registers, other.registers)) false else true
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        result = result * PRIME + java.util.Arrays.hashCode(this.registers)
        return result
    }

    protected fun canEqual(other: Any): Boolean {
        return other is Registers
    }

    override fun toString(): String {
        return "sml.Registers(registers=" + java.util.Arrays.toString(this.registers) + ")"
    }

    companion object {

        private val NUMBEROFREGISTERS = 32
    }
}
