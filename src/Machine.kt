import java.io.File
import java.io.IOException
import java.util.Scanner

/*
 * The machine language interpreter
 */
data class Machine(var pc: Int) {
    // The labels in the SML program, in the order in which
    // they appear (are defined) in the program

    val labels: Labels

    // The SML program, consisting of prog.size() instructions, each
    // of class Instruction (or one of its subclasses)
    val prog: MutableList<Instruction>

    // The registers of the SML machine
    val registers: Registers

    // The program counter; it contains the index (in prog) of
    // the next instruction to be executed.

    init {
        labels = Labels()
        prog = ArrayList<Instruction>()
        registers = Registers()
    }

    // Print the program

    override fun toString(): String {
        val s = StringBuffer()
        for (i in 0 until prog.size)
            s.append(prog[i].toString() + "\n")
        return s.toString()
    }

    // Execute the program in prog, beginning at instruction 0.
    // Precondition: the program and its labels have been store properly.

    fun execute() {
        while (pc < prog.size) {
            val ins = prog[pc]
            pc++
            ins.execute(this)
        }
    }

    private val PATH = System.getProperty("user.dir") + "/"

    private var line: String = ""

    // translate the small program in the file into lab (the labels) and prog (the program)
    // return "no errors were detected"
    fun readAndTranslate(file: String): Boolean {
        val fileName = PATH + file // source file of SML code
        try {
            Scanner(File(fileName)).use { sc ->
                // Scanner attached to the file chosen by the user
                labels.reset()
                prog.clear()

                // Each iteration processes line and reads the next line into line
                while (sc.hasNext()) {
                    line = sc.nextLine()
                    // Store the label in label
                    val label = scan()

                    if (label.length > 0) {
                        val ins = getInstruction(label)
                        if (ins != null) {
                            labels.addLabel(label)
                            prog.add(ins)
                        }
                    }
                }
            }
        } catch (ioE: IOException) {
            println("File: IO error " + ioE.message)
            return false
        }

        return true
    }

    // line should consist of an MML instruction, with its label already
    // removed. Translate line into an instruction with label label
    // and return the instruction
    fun getInstruction(label: String): Instruction? {
        val s1: Int // Possible operands of the instruction
        val s2: Int
        val r: Int
        val x: Int

        if (line == "")
            return null

        val ins = scan()
        when (ins) { // replace with reflection
            "add" -> {
                r = scanInt()
                s1 = scanInt()
                s2 = scanInt()
                return AddInstruction(label, r, s1, s2)
            }
            "lin" -> {
                r = scanInt()
                s1 = scanInt()
                return LinInstruction(label, r, s1)
            }
            else -> {
                return NoOpInstruction(label, line)
            }
        // You will have to write code here for the other instructions.
        }
    }

    /*
     * Return the first word of line and remove it from line. If there is no
     * word, return ""
     */
    private fun scan(): String {
        line = line.trim { it <= ' ' }
        if (line.length == 0)
            return ""

        var i = 0
        while (i < line.length && line[i] != ' ' && line[i] != '\t') {
            i = i + 1
        }
        val word = line.substring(0, i)
        line = line.substring(i)
        return word
    }

    // Return the first word of line as an integer. If there is
    // any error, return the maximum int
    private fun scanInt(): Int {
        val word = scan()
        if (word.length == 0) {
            return Integer.MAX_VALUE
        }

        try {
            return Integer.parseInt(word)
        } catch (e: NumberFormatException) {
            return Integer.MAX_VALUE
        }

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            if (args.size != 1) {
                System.err.println("Incorrect number of arguments - Machine <file> - required")
                System.exit(-1)
            }

            val m = Machine(0) // initialise to first instruction
            if (!m.readAndTranslate(args[0])) { // convert and add to machine
                println("Translation phase failed!!")
            } else {
                println("Here is the program; it has " + m.prog.size + " instructions.")
                println(m)

                print("Beginning program execution...")
                m.execute()
                println("Ending program execution.")

                println("Values of registers at program termination:")
                println("" + m.registers + ".")
            }
        }
    }
}
