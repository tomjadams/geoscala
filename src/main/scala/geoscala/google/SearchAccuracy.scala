package geoscala.google

object SearchAccuracy {

}

sealed trait SearchAccuracy {
  def accuracy: Int
}

