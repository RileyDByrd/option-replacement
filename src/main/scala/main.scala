package byrd.riley.optionreplacement

@main
def main(): Unit =
  val thing: String | Null = null


  println("FlatOption Calls")
  import FlatOptionModule.FlatOption
  import FlatOptionModule.FlatOption.*

  val flatOption3: FlatOption[String] = FlatSome("three")

  val flatOptionNo: FlatOption[String] = FlatNone

  println("Maps")
  // Map Yes declared as FlatOption.
  println(flatOption3.map(numString => s"$numString of something, mapped").toFlatOptionString)
  // Map Yes defined inline.
  println(FlatSome(3).map(num => num + 1).toFlatOptionString)
  // Map No declared as FlatOption.
  println(flatOptionNo.map(numString => s"$numString of something, mapped").toFlatOptionString)

  println("\nFlatMaps")
  // FlatMap Yes declared as FlatOption to Yes.
  println(flatOption3.flatMap(numString => FlatSome(s"$numString of something, flatMapped")).toFlatOptionString)
  // FlatMap Yes defined inline.
  println(FlatSome(3).flatMap(num => FlatSome(num + 5)).toFlatOptionString)
  // FlatMap No declared as FlatOption.
  println(flatOptionNo.flatMap(numString => FlatSome(s"$numString of something, flatMapped")).toFlatOptionString)


  
  println("\nDisjunction Calls")
  import DisjunctionModule.*
  import Disjunction.*

  val disjunctionHello: Disjunction[Int, String] = Dexter("Hello")

  val disjunction3: Disjunction[Int, String] = Sinister(3)

  println("Maps")
  // Map Dexter declared as Disjunction.
  println(disjunctionHello.map(helloString => s"$helloString of something, mapped"))
  // Map Dexter defined inline.
  println(Dexter[String, Int](3).map(num => num + 10))
  // Map Sinister declared as Disjunction.
  println(disjunction3.map(num => s"$num of something, mapped"))

  println("\nFlatMaps")
  // FlatMap Dexter declared as Disjunction.
  println(disjunctionHello.flatMap(helloString => Dexter(s"$helloString of something, flatMapped")))
  // FlatMap Dexter defined inline.
  println(Dexter[String, Int](3).flatMap(num => Dexter(num + 20)))
  // FlatMap Sinister declared as Disjunction
  println(disjunction3.flatMap(num => Dexter(s"$num of something, flatMapped")))


  println("\nMethod calls with numeric types")
  // Converts to Int input to Long and doesn't confuse related numeric types through multiple method calls.
  def produceDisjunction: Disjunction[Long, Int] = Sinister(3)

  def mapDisjunction(disjunction: Disjunction[Long, Int]): Disjunction[Long, Int] = disjunction.map(_ + 50)

  mapDisjunction(produceDisjunction) match
    case myLong: Long => println(s"long $myLong")
    case myInt: Int => println(s"int $myInt")

  println("\nCalling boolean methods")
  println(s"disjunctionHello isDexter: ${disjunctionHello.isDexter}")
  println(s"disjunctionHello isSinister: ${disjunctionHello.isSinister}")
  println(s"disjunction3 isDexter: ${disjunction3.isDexter}")
  println(s"disjunction3 isSinister: ${disjunction3.isSinister}")

  println("\nConversion to Either")
  println(s"disjunctionHello.toEither: ${disjunctionHello.toEither}")
  println(s"disjunction3.toEither: ${disjunction3.toEither}")