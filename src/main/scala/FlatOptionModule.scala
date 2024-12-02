package byrd.riley.optionreplacement

import scala.CanEqual.derived

object FlatOptionModule:
  //noinspection ScalaWeakerAccess
  type NonNull = AnyVal | AnyRef
  
  opaque type FlatOption[+A <: NonNull] = A | Null
  opaque type FlatSome[+A <: NonNull] <: FlatOption[A] = A
  opaque type FlatNone <: FlatOption[Nothing] = Null

  given flatOptionCanEqualFlatNone[A <: NonNull]: CanEqual[FlatOption[A], FlatNone] = derived
  given flatNoneCanEqualFlatOption[A <: NonNull]: CanEqual[FlatNone, FlatOption[A]] = derived
  
  object FlatOption:
    inline def apply[A <: NonNull](value: A | Null): FlatOption[A] = value

    extension[A <: NonNull] (flatOption: FlatOption[A])
      transparent inline def biMap[C, B](noneFunc: => C, someFunc: A => B): C | B =
        if flatOption == FlatNone
        then noneFunc
        else someFunc(flatOption.nn)

      def flatMap[B <: NonNull](func: A => FlatOption[B]): FlatOption[B] =
        biMap(noneFunc = FlatNone, someFunc = func)

      inline def map[B <: NonNull](inline func: A => B): FlatOption[B] =
        flatMap((input: A) => FlatSome(func(input)))

      def isEmpty: Boolean = flatOption == FlatNone
      def isDefined: Boolean = flatOption != FlatNone
      transparent inline def toNullable: A | Null = flatOption
      inline def toOption: Option[A] = Option(flatOption).map(_.nn)
      def toFlatOptionString: String = biMap(noneFunc = "FlatNone", someFunc = some => s"FlatSome($some)")

    object FlatSome:
      inline def apply[A <: NonNull](value: A): FlatSome[A] = value

      def unapply[A <: NonNull](flatOption: FlatOption[A]): Option[A] = flatOption.toOption

    val FlatNone: FlatNone = null