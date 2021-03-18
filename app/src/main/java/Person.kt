import java.lang.reflect.Array

class Person{
    var name: String? = null
    var age: String? = null
}

class A{


    fun x(){
    }
}

fun main() {
    val p = Person().apply {
        name = "xyz"
    }

    print(p.name)
}
