import scala.util.Random
import scala.io.StdIn.readLine

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

object Game:
  def fire(wasps: List[Wasp]): Unit =
    val aliveWasps = wasps.filter(w => !w.isDead)

    if aliveWasps.nonEmpty then
      val random = Random.nextInt(aliveWasps.length)
      val target = aliveWasps(random)

      println(s"\n One ${target.getClass.getSimpleName} was struck by your fire.")
      target.takeHit()

@main def startWaspGame(): Unit =
  println("Welcome to the Wasp Game!")
  setupNewGame()

def setupNewGame(): Unit =
  val nestWasps = Nest.createWasps()
  processCommand(nestWasps)

def processCommand(wasps: List[Wasp]): Unit =
  val queen = wasps.head
  val input = readLine("Enter your command: ").toLowerCase.trim

  input match
    case "fire" => 
      if queen.isDead || wasps.forall(w => w.isDead) then
        println("\nThe game is already over! Type 'restart' to play again.")
        processCommand(wasps)
      else 
        Game.fire(wasps)
        Nest.displayStatus(wasps)

        if queen.isDead || wasps.forall(w => w.isDead) then
          println("\nGAME OVER!")
          println("Type 'restart' to play again or 'quit' to exit.")
          processCommand(wasps)
        else
          processCommand(wasps)

    case "restart" => 
      setupNewGame()

    case "quit" =>
      println("Thanks for playing!")

    case _ => 
      println("Invalid command. Please enter 'fire', 'restart', or 'quit'.")  
      processCommand(wasps)

  
