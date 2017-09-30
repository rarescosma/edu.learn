import scala.collection.immutable.HashSet
import scala.collection.immutable.HashMap

class UnionFind[T](all: Array[T]) {
	private var dataStruct = new HashMap[T, Wrap]

	for (a <- all if( a != null ) )
		dataStruct = dataStruct + (a -> new Wrap(a))

	/**
	 * The number of Unions
	 */
	private var size = dataStruct.size
	
	def sizeUnion: Int = size


	/**
	 * Unions the set containing a and b
	 */
	def union(a: T, b: T): Wrap = {
		val first: Wrap = dataStruct.get(a).get
		val second: Wrap = dataStruct.get(b).get
		if (first.contains(b) || second.contains(a))
			first
		else {
			//below is to merge smaller with bigger rather than other way around
			val firstIsBig = (first.set.size > second.set.size)
			val ans = if (firstIsBig) {
				_performUnion(first, second)
			} else {
				_performUnion(second, first)
			}
			size = size - 1
			ans
		}
	}

	def _performUnion(first: Wrap, second: Wrap): Wrap = {
		first.set = first.set ++ second.set

		second.set.foreach(a => {
			dataStruct = dataStruct - a
			dataStruct = dataStruct + (a -> first)
		})

		first
	}

	/**
	 * true if they are in same set. false if not
	 */
	def find(a: T, b: T): Boolean = dataStruct.get(a).get.contains(b)

	class Wrap(e: T) {
		var set = new HashSet[T]
		set = set + e

		def add(elem: T) {
			set = set + elem
		}

		def contains(elem: T): Boolean = set.contains(elem)
	}
}
