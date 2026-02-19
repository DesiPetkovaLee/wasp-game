trait Wasp:
  val deduction: Int
  var hitPoints: Int

  def isDead: Boolean = hitpoints <= 0

  def takeHit(): Unit = 
  hitPoints = Math.max(0, hitPoints - deduction)

  class Queen extends Wasp:
    val deduction = 7
    var hitPoints = 80

  class Worker extends Wasp:
    val deduction = 10
    var hitPoints = 68

  class Drone extends Wasp:
    val deduction = 12
    var hitPoints = 60



