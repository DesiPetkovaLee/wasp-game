trait Wasp:
  val deduction: Int
  var hitPoints: Int

  def isDead: Boolean = hitPoints <= 0

  def takeHit(): Unit = 
    hitPoints = Math.max(0, hitPoints - deduction)

  override def toString: String =
    val name = this.getClass.getSimpleName
    s"$name: hitPoints = ($hitPoints hit points)"

class Queen extends Wasp:
  val deduction = 7
  var hitPoints = 80

class Worker extends Wasp:
  val deduction = 10
  var hitPoints = 68

class Drone extends Wasp:
  val deduction = 12
  var hitPoints = 60

object Nest:
  def createWasps(): List[Wasp] = 
    val queen = List(new Queen())
    val workers = List.fill(5)(new Worker())
    val drones = List.fill(8)(new Drone())
    queen ++ workers ++ drones

  def displayStatus(wasps: List[Wasp]): Unit =
    println("\nScanning the nest... Here's what's left ->" )
    wasps.foreach(println)