import java.util.*

//An instance contains a list of Strings, called "labels",
//in the order in which they were added to the list.

class Labels {
    private var labels: MutableList<String> = ArrayList<String>()

    // Add label lab to this list and return its number in the list
    // (the first one added is number 0)
    // Precondition: the list has at most 49 entries

    fun addLabel(lab: String): Int {
        labels.add(lab)
        return labels.size - 1
    }

    // = the number of label lab in the list
    // (= -1 if lab is not in the list)

    fun indexOf(lab: String): Int {

        // invariant: lab is not in labels[0..i-1]
        for (i in labels.indices) {
            if (lab == labels[i]) {
                return i
            }
        }
        return -1
    }

    // copy of original list
    fun getLabel(): List<String> = labels.toList()

    // representation of this instance, "(label 0, label 1, ..., label (n-1))"
    override fun toString(): String {
        val r = StringBuilder("(")
        // invariant: r contains the representation for labels[0..i-1]
        // (with the opening "(" but no closing ")")
        for (i in labels.indices) {
            if (i == 0) {
                r.append(labels[i])
            } else {
                r.append(", ").append(labels[i])
            }
        }
        r.append(")")
        return r.toString()
    }

    // Set the number of elements in the list to 0

    fun reset() {
        labels.clear()
    }
}
